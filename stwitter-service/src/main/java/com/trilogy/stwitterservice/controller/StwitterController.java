package com.trilogy.stwitterservice.controller;

import com.trilogy.stwitterservice.model.PostAndCommentsViewModel;
import com.trilogy.stwitterservice.service.StwitterServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
@CacheConfig(cacheNames = {"pcvms"})
public class StwitterController {

    @Autowired
    private StwitterServiceLayer stwitter;

    @CachePut(key = "#result.getPost_id()")
    @PostMapping
    public PostAndCommentsViewModel createAPost(@RequestBody @Valid PostAndCommentsViewModel pcvm) {
        System.out.println("Putting pvcm in cache");
        return stwitter.createPostAndComments(pcvm);
    }


    @Cacheable
    @GetMapping(value = "/{id}")
    public PostAndCommentsViewModel getPostById(@PathVariable int id) {
        System.out.println("Retrieving with GET /posts/{id} instead of retrieving with cache");
        return stwitter.getPostById(id);
    }


    @GetMapping(value = "/user/{posterName}")
    public List<PostAndCommentsViewModel> getPostByPosterName(@PathVariable String posterName) {
        return stwitter.getPostByPosterName(posterName);
    }

}
