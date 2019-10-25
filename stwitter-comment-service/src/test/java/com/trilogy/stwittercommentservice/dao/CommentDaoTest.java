package com.trilogy.stwittercommentservice.dao;

import com.trilogy.stwittercommentservice.model.Comment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CommentDaoTest {

    @Autowired
    CommentDao commentDao;

    @Before
    public void setUp() {
        List<Comment> allComments = commentDao.findAllComments();

        allComments.stream()
                .forEach(comment -> commentDao.deleteAComment(comment.getCommentId()));
    }

    @Test
    public void createGetDeleteComment() {
        Comment comment = new Comment(
                1,
                "Arturo",
                LocalDate.of(2019,07,13),
                "This is a comment made from the createGetDelete test");

        Comment commentAdded = commentDao.makeAComment(comment);

        int id = commentAdded.getCommentId();

        comment.setCommentId(id);
        Assert.assertEquals(comment, commentAdded);

        Comment commentFound = commentDao.getCommentById(id);
        Assert.assertEquals(comment, commentFound);

        String deleteMessage = "This comment has been deleted.";
        Assert.assertEquals(deleteMessage, commentDao.deleteAComment(id));

        String noDeleteMessage = "No comment with comment_id 0 was found. No comment deleted.";
        Assert.assertEquals(noDeleteMessage, commentDao.deleteAComment(0));
    }

    @Test
    public void updateComment() {
        Comment comment = new Comment(
                1,
                "Arturo",
                LocalDate.of(2019,07,13),
                "This is a comment made from the createGetDelete test");

        Comment commentAdded = commentDao.makeAComment(comment);

        int id = commentAdded.getCommentId();
        comment.setCommentId(id);
        comment.setComment("Update: I am changing this comment. Muahahaha!");

        Comment updatedComment = commentDao.updateComment(comment);
        Assert.assertEquals(comment, updatedComment);
    }

    @Test
    public void getAllCommentsForAPost() {

        Comment comment1 = new Comment(
                3,
                "Arturo",
                LocalDate.of(2019,07,13),
                "First comment for postId 3");

        Comment comment2 = new Comment(
                3,
                "Arturo",
                LocalDate.of(2019,07,13),
                "Second comment for postId 3");

        Comment comment3 = new Comment(
                2,
                "Arturo",
                LocalDate.of(2019,07,13),
                "First comment for postId 2");

        commentDao.makeAComment(comment1);
        commentDao.makeAComment(comment2);
        commentDao.makeAComment(comment3);

        List<Comment> commentsForPost3 = commentDao.findAllCommentsByPostId(3);
        List<Comment> commentsForPost2 = commentDao.findAllCommentsByPostId(2);
        List<Comment> commentsForPost5 = commentDao.findAllCommentsByPostId(0);

        Assert.assertEquals(2, commentsForPost3.size());
        Assert.assertEquals(1, commentsForPost2.size());
        Assert.assertEquals(0, commentsForPost5.size());


    }
}
