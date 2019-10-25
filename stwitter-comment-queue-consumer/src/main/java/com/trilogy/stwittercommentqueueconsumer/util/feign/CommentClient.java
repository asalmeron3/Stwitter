package com.trilogy.stwittercommentqueueconsumer.util.feign;

import com.trilogy.stwittercommentqueueconsumer.util.messages.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "stwitter-comment-service")
public interface CommentClient {

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Comment createComment(@RequestBody Comment comment);

    @RequestMapping(value = "/comment", method = RequestMethod.PUT)
    public Comment updateComment(@RequestBody Comment comment);

}
