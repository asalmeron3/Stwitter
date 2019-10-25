package com.trilogy.stwitterservice.feignClients;

import com.trilogy.stwitterservice.model.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "stwitter-post-service")
public interface PostClient {
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    Post createPost(Post post);

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    Post getPostById(@PathVariable int id);

    @RequestMapping(value = "/post/user/{name}", method = RequestMethod.GET)
    List<Post> getPostsByPosterName(@PathVariable String name);

}
