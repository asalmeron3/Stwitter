package com.trilogy.stwitterpostservice.dao;

import com.trilogy.stwitterpostservice.model.Post;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PostDaoJdbcTemplateTest {

    @Autowired
    PostDao postDao;

    @Before
    public void setUp() {
        List<Post> posts = postDao.findAllPosts();
        posts.stream()
                .forEach(post -> postDao.deleteAPost(post.getPost_id()));
    }

    @Test
    public void makeGetDeleteAPost() {
        Post post = new Post(
            LocalDate.of(2017,07,15),
            "Arturo",
            "This is a test post"
        );

        Post postCreated = postDao.makeAPost(post);

        int id = postCreated.getPost_id();

        post.setPost_id(id);
        Assert.assertEquals(post, postCreated);

        Post postFound = postDao.getPostById(id);
        Assert.assertEquals(post, postFound);

        String deletionFailedResponse =  "No post with post_id " + 0 + " was found. No post deleted.";
        String deletionSuccessfulResponse = "Post with post_id " + id + " has been deleted.";

        Assert.assertEquals(deletionFailedResponse, postDao.deleteAPost(0));
        Assert.assertEquals(deletionSuccessfulResponse, postDao.deleteAPost(id));

    }


    @Test
    public void updatePost() {
        Post post = new Post(
                LocalDate.of(2017,07,15),
                "Arturo",
                "This is a test post"
        );

        Post postCreated = postDao.makeAPost(post);

        int id = postCreated.getPost_id();

        post.setPost_id(id);
        post.setPost("This is an update to the post");

        Post updatedPost = postDao.updatePost(post);
        Assert.assertEquals(post, updatedPost);

    }

    @Test
    public void findAllPosts() {

        Post post0 = new Post(
                LocalDate.of(2017,07,15),
                "Arturo",
                "This is test post 0"
        );
        postDao.makeAPost(post0);

        Post post1 = new Post(
                LocalDate.of(2017,07,15),
                "Arturo",
                "This is test post 1"
        );
        postDao.makeAPost(post1);

        Post post2 = new Post(
                LocalDate.of(2017,07,15),
                "Arturo",
                "This is test post 2"
        );
        postDao.makeAPost(post2);

        List<Post> posts = postDao.findAllPosts();

        Assert.assertEquals(3, posts.size());
    }

}