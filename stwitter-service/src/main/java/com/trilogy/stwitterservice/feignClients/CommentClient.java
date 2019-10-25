package com.trilogy.stwitterservice.feignClients;

import com.trilogy.stwitterservice.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "stwitter-comment-service")
public interface CommentClient {

    @RequestMapping(value = "/comment/post/{post_id}", method = RequestMethod.GET)
    List<Comment> getCommentsByPostId(@PathVariable int post_id);

}
