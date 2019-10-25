package com.trilogy.stwitterservice.service;


import com.trilogy.stwitterservice.feignClients.CommentClient;
import com.trilogy.stwitterservice.feignClients.PostClient;
import com.trilogy.stwitterservice.model.Comment;
import com.trilogy.stwitterservice.model.Post;
import com.trilogy.stwitterservice.model.PostAndCommentsViewModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class StwitterServiceLayerTest {

    StwitterServiceLayer stwitter;

    @Mock
    RabbitTemplate rabbitTemplate;

    @Mock
    CommentClient commentClient;

    @Mock
    PostClient postClient;

    Comment comment1WithPostId = new Comment();
    Comment comment2WithPostId = new Comment();
    Comment comment3WithId = new Comment();
    Post post1WithId = new Post();
    Post post2WithId = new Post();

    public void instantiateCommentsAndPostsForAllTests() {
        comment1WithPostId.setPostId(1);
        comment1WithPostId.setCommentId(1);
        comment1WithPostId.setCommenterName("Luis");
        comment1WithPostId.setComment("The first comment");
        comment1WithPostId.setDateCreated(LocalDate.of(2019,07,13));

        comment2WithPostId.setPostId(1);
        comment2WithPostId.setCommentId(2);
        comment2WithPostId.setCommenterName("Aaron");
        comment2WithPostId.setComment("The 2nd comment");
        comment2WithPostId.setDateCreated(LocalDate.of(2019,07,13));

        comment3WithId.setPostId(2);
        comment3WithId.setCommentId(3);
        comment3WithId.setCommenterName("Bill");
        comment3WithId.setComment("Bill's 1st comment");
        comment3WithId.setDateCreated(LocalDate.of(2019,07,14));

        post1WithId.setPoster_name("Arturo");
        post1WithId.setPost_date(LocalDate.of(2017,07,12));
        post1WithId.setPost("This is a test post");
        post1WithId.setPost_id(1);

        post2WithId.setPoster_name("Arturo");
        post2WithId.setPost_date(LocalDate.of(2017,07,15));
        post2WithId.setPost("This is a 2nd test post");
        post2WithId.setPost_id(2);
    }

    @Before
    public void setup() {
        mockCommentsAndPost();
        instantiateCommentsAndPostsForAllTests();
        stwitter = new StwitterServiceLayer(rabbitTemplate, commentClient, postClient);
    }

    public void mockCommentsAndPost() {

        // Comments and post for first post (with no ids)
        Comment comment1 = new Comment();
        comment1.setCommenterName("Luis");
        comment1.setDateCreated(LocalDate.of(2019,07,13));
        comment1.setComment("The first comment");

        Comment comment2 = new Comment();
        comment2.setCommenterName("Aaron");
        comment2.setDateCreated(LocalDate.of(2019, 07, 13));
        comment2.setComment("The 2nd comment");

        List<Comment> commentsPost1 = new ArrayList<>();
        commentsPost1.add(comment1);
        commentsPost1.add(comment2);

        Post post1 = new Post();
        post1.setPoster_name("Arturo");
        post1.setPost_date(LocalDate.of(2017,07,12));
        post1.setPost("This is a test post");


        // Comments and post for second post (with no ids)
        Comment comment3 = new Comment();
        comment3.setComment("Bill's 1st comment");
        comment3.setCommenterName("Bill");
        comment3.setDateCreated(LocalDate.of(2019,07,14));

        List<Comment> comentsPost2 = new ArrayList<>();
        comentsPost2.add(comment3);

        Post post2 = new Post();
        post2.setPoster_name("Arturo");
        post2.setPost_date(LocalDate.of(2017,07,15));
        post2.setPost("This is a 2nd test post");


        // List of Comments with Ids
        List<Comment> commentsPost1WithIds = new ArrayList<>();
        commentsPost1WithIds.add(comment1WithPostId);
        commentsPost1WithIds.add(comment2WithPostId);

        List<Comment> comentsPost2WithIds = new ArrayList<>();
        comentsPost2WithIds.add(comment3WithId);

        // List of Posts with Ids
        List<Post> arturosPosts = new ArrayList<>();
        arturosPosts.add(post1WithId);
        arturosPosts.add(post2WithId);

        // mock the responses you want when calling methods in your service layer
        doReturn(post1WithId).when(postClient).createPost(post1);
        doReturn(commentsPost1WithIds).when(commentClient).getCommentsByPostId(1);

        doReturn(post2WithId).when(postClient).createPost(post2);
        doReturn(comentsPost2WithIds).when(commentClient).getCommentsByPostId(2);

        doReturn(post1WithId).when(postClient).getPostById(1);
        doReturn(post2WithId).when(postClient).getPostById(2);

        doReturn(arturosPosts).when(postClient).getPostsByPosterName("Arturo");
    }

    @Test
    public void createAPostAndCommentViewModel() {
        Comment comment1 = new Comment();
        comment1.setCommenterName("Luis");
        comment1.setDateCreated(LocalDate.of(2019,07,13));
        comment1.setComment("The first comment");

        Comment comment2 = new Comment();
        comment2.setCommenterName("Aaron");
        comment2.setDateCreated(LocalDate.of(2019, 07, 13));
        comment2.setComment("The 2nd comment");

        List<Comment> commentsPost1 = new ArrayList<>();
        commentsPost1.add(comment1);
        commentsPost1.add(comment2);

        PostAndCommentsViewModel pvcm1ToPost = new PostAndCommentsViewModel();
        pvcm1ToPost.setComments(commentsPost1);
        pvcm1ToPost.setPoster_name("Arturo");
        pvcm1ToPost.setPost_date(LocalDate.of(2017,07,12));
        pvcm1ToPost.setPost("This is a test post");

        PostAndCommentsViewModel actualPcvm = stwitter.createPostAndComments(pvcm1ToPost);


        // Building 2nd view model (no ids)
        Comment comment3 = new Comment();
        comment3.setCommenterName("Bill");
        comment3.setComment("Bill's 1st comment");
        comment3.setDateCreated(LocalDate.of(2019,07,14));

        List<Comment> commentsPost2 = new ArrayList<>();
        commentsPost2.add(comment3);

        PostAndCommentsViewModel pvcm2ToPost = new PostAndCommentsViewModel();
        pvcm2ToPost.setComments(commentsPost2);
        pvcm2ToPost.setPoster_name("Arturo");
        pvcm2ToPost.setPost_date(LocalDate.of(2017,07,15));
        pvcm2ToPost.setPost("This is a 2nd test post");


        // Building Expected view model for post 1
        List<Comment> commentsPost1WithIds = new ArrayList<>();
        commentsPost1WithIds.add(comment1WithPostId);
        commentsPost1WithIds.add(comment2WithPostId);

        PostAndCommentsViewModel expectedPcvm = new PostAndCommentsViewModel();
        expectedPcvm.setComments(commentsPost1WithIds);
        expectedPcvm.setPoster_name("Arturo");
        expectedPcvm.setPost_date(LocalDate.of(2017,07,12));
        expectedPcvm.setPost("This is a test post");
        expectedPcvm.setPost_id(1);


        // Building expected view model for post 2
        List<Comment> commentsPost2WithIds = new ArrayList<>();
        commentsPost2WithIds.add(comment3WithId);

        PostAndCommentsViewModel expectedPcvm2 = new PostAndCommentsViewModel();
        expectedPcvm2.setComments(commentsPost2WithIds);
        expectedPcvm2.setPoster_name("Arturo");
        expectedPcvm2.setPost_date(LocalDate.of(2017,07,15));
        expectedPcvm2.setPost("This is a 2nd test post");
        expectedPcvm2.setPost_id(2);

        PostAndCommentsViewModel actualPcvm2 = stwitter.createPostAndComments(pvcm2ToPost);


        // Asserting equality
        Assert.assertEquals(expectedPcvm, actualPcvm);
        Assert.assertEquals(expectedPcvm2, actualPcvm2);

    }

    @Test
    public void getPostAndCommentViewModelById() {
        // Expected pcvm2
        List<Comment> commentsPost2WithIds = new ArrayList<>();
        commentsPost2WithIds.add(comment3WithId);

        PostAndCommentsViewModel expectedPcvm = new PostAndCommentsViewModel();
        expectedPcvm.setComments(commentsPost2WithIds);
        expectedPcvm.setPoster_name("Arturo");
        expectedPcvm.setPost_date(LocalDate.of(2017,07,15));
        expectedPcvm.setPost("This is a 2nd test post");
        expectedPcvm.setPost_id(2);

        PostAndCommentsViewModel actualPcvm = stwitter.getPostById(2);


        // Expected pcvm1
        List<Comment> commentsPost1WithIds = new ArrayList<>();
        commentsPost1WithIds.add(comment1WithPostId);
        commentsPost1WithIds.add(comment2WithPostId);

        PostAndCommentsViewModel expectedPcvm1 = new PostAndCommentsViewModel();
        expectedPcvm1.setComments(commentsPost1WithIds);
        expectedPcvm1.setPoster_name("Arturo");
        expectedPcvm1.setPost_date(LocalDate.of(2017,07,12));
        expectedPcvm1.setPost("This is a test post");
        expectedPcvm1.setPost_id(1);

        PostAndCommentsViewModel actualPcvm1 = stwitter.getPostById(1);

        // Asserting equality
        Assert.assertEquals(expectedPcvm, actualPcvm);
        Assert.assertEquals(expectedPcvm1, actualPcvm1);

    }

    @Test
    public void getPostAndCommentViewModelByPosterName() {
        List<Comment> commentsPost1WithIds = new ArrayList<>();
        commentsPost1WithIds.add(comment1WithPostId);
        commentsPost1WithIds.add(comment2WithPostId);

        PostAndCommentsViewModel pcvm1 = new PostAndCommentsViewModel();
        pcvm1.setComments(commentsPost1WithIds);
        pcvm1.setPoster_name("Arturo");
        pcvm1.setPost_date(LocalDate.of(2017,07,12));
        pcvm1.setPost("This is a test post");
        pcvm1.setPost_id(1);

        List<Comment> commentsPost2WithIds = new ArrayList<>();
        commentsPost2WithIds.add(comment3WithId);

        PostAndCommentsViewModel pcvm2 = new PostAndCommentsViewModel();
        pcvm2.setComments(commentsPost2WithIds);
        pcvm2.setPoster_name("Arturo");
        pcvm2.setPost_date(LocalDate.of(2017,07,15));
        pcvm2.setPost("This is a 2nd test post");
        pcvm2.setPost_id(2);

        List<PostAndCommentsViewModel> arturosPcvms = new ArrayList<>();
        arturosPcvms.add(pcvm1);
        arturosPcvms.add(pcvm2);

        List<PostAndCommentsViewModel> pcvmsFromService = stwitter.getPostByPosterName("Arturo");

        Assert.assertEquals(arturosPcvms, pcvmsFromService);
        Assert.assertEquals(2, pcvmsFromService.size());
    }
}