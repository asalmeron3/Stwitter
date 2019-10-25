package com.trilogy.stwittercommentservice.dao;

import com.trilogy.stwittercommentservice.model.Comment;

import java.util.List;

public interface CommentDao {
    List<Comment> findAllComments();
    List<Comment> findAllCommentsByPostId(int postId);

    Comment makeAComment(Comment comment);
    Comment getCommentById(int commentId);
    Comment updateComment(Comment comment);

    String deleteAComment(int id);


}
