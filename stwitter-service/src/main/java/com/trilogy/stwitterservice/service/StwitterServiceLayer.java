package com.trilogy.stwitterservice.service;

import com.trilogy.stwitterservice.feignClients.CommentClient;
import com.trilogy.stwitterservice.feignClients.PostClient;
import com.trilogy.stwitterservice.model.Comment;
import com.trilogy.stwitterservice.model.Post;
import com.trilogy.stwitterservice.model.PostAndCommentsViewModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StwitterServiceLayer {

    public static final String EXCHANGE = "comment-exchange";
    public static final String ROUTING_KEY = "comment.create.controller";


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CommentClient commentClient;

    @Autowired
    private PostClient postClient;

    public StwitterServiceLayer(RabbitTemplate rabbitTemplate, CommentClient commentClient, PostClient postClient) {
        this.rabbitTemplate = rabbitTemplate;
        this.commentClient = commentClient;
        this.postClient = postClient;
    }

    public PostAndCommentsViewModel createPostAndComments(PostAndCommentsViewModel pcvm) {

        Post post = new Post(pcvm.getPost_date(), pcvm.getPoster_name(), pcvm.getPost());
        post = postClient.createPost(post);

        int id = post.getPost_id();

        if (pcvm.getComments() != null) {
            pcvm.getComments().stream()
                    .forEach(comment -> {
                        comment.setPostId(id);
                        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, comment);
                    });
        }

        pcvm.setPost_id(id);

        List<Comment> commentsWithIds = commentClient.getCommentsByPostId(id);

        if (commentsWithIds.size() > 0) {
            pcvm.setComments(commentsWithIds);
        }

        return pcvm;
    }

    public PostAndCommentsViewModel getPostById(int id) {

        Post post = postClient.getPostById(id);
        List<Comment> comments = commentClient.getCommentsByPostId(id);

        PostAndCommentsViewModel pcvm = new PostAndCommentsViewModel(
                post.getPost_id(),
                post.getPost_date(),
                post.getPoster_name(),
                post.getPost(),
                comments
        );

        return pcvm;
    }

    public List<PostAndCommentsViewModel> getPostByPosterName(String posterName) {

        List<Post> posts = postClient.getPostsByPosterName(posterName);

        List<PostAndCommentsViewModel> pcvms = new ArrayList<>();

        posts.stream()
                .forEach(post -> {
                    int postId = post.getPost_id();

                    List<Comment> comments = commentClient.getCommentsByPostId(postId);

                    PostAndCommentsViewModel pcvm = new PostAndCommentsViewModel(
                            postId,
                            post.getPost_date(),
                            post.getPoster_name(),
                            post.getPost(),
                            comments
                    );

                    pcvms.add(pcvm);
                });
        return pcvms;

    }
}
