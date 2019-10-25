package com.trilogy.stwittercommentservice.controller;

import com.trilogy.stwittercommentservice.dao.CommentDaoJdbcTemplate;
import com.trilogy.stwittercommentservice.exception.NotFoundException;
import com.trilogy.stwittercommentservice.model.Comment;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/comment")
@CacheConfig(cacheNames = {"comments"})
public class CommentController {

    @Autowired
    private CommentDaoJdbcTemplate commentJdbc;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> getAllComments() {
        return commentJdbc.findAllComments();
    }

    @Cacheable
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment getCommentById(@PathVariable int id) {
        try {
            return commentJdbc.getCommentById(id);
        } catch (Error e) {
            return null;
        }
    }

    @GetMapping("/post/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> getCommentsByPostId(@PathVariable int post_id) {
        return commentJdbc.findAllCommentsByPostId(post_id);
    }

    @CachePut(value = "#result.getCommentId()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createAComment(@RequestBody @Valid Comment comment) {
        return commentJdbc.makeAComment(comment);
    }

    @CacheEvict(value = "#result.getCommentId()")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Comment updateComment(@RequestBody @Valid Comment comment) {
        Comment updatedComment = commentJdbc.updateComment(comment);
        if (updatedComment != null) {
            return updatedComment;
        } else {
            throw new NotFoundException("Could not find a comment with commentId " + comment.getCommentId() +
                    ". No comment was updated.");
        }
    }

    @CacheEvict
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteCommentById(@PathVariable int id) {
        return commentJdbc.deleteAComment(id);
    }


}
