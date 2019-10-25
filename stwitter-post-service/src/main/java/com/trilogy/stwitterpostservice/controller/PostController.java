package com.trilogy.stwitterpostservice.controller;

import com.trilogy.stwitterpostservice.dao.PostDaoJdbcTemplate;
import com.trilogy.stwitterpostservice.exception.NotFoundException;
import com.trilogy.stwitterpostservice.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/post")
@CacheConfig(cacheNames = {"posts"})
public class PostController {

    @Autowired
    private PostDaoJdbcTemplate postJdbc;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getAllPosts() {
        return postJdbc.findAllPosts();
    }

    @GetMapping("/user/{poster_name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getAllPostsByPosterName(@PathVariable String poster_name) {
        return postJdbc.findAllPostsByPosterName(poster_name);
    }

    @Cacheable
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post getPostById(@PathVariable int id) {
        try {
            return postJdbc.getPostById(id);
        } catch (Error e) {
            return null;
        }
    }

    @CachePut(key = "#result.getPost_id()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createAPost(@RequestBody @Valid Post post) {
        return postJdbc.makeAPost(post);
    }

    @CacheEvict(key = "#rsvp.getPost_id()")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Post updatePost(@RequestBody @Valid Post post) {
        Post updatedPost = postJdbc.updatePost(post);
        if (updatedPost != null) {
            return updatedPost;
        } else {
            throw new NotFoundException("Could not find a post with postId " + post.getPost_id() +
                    ". No post was updated.");
        }
    }

    @CacheEvict
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deletePostById(@PathVariable int id) {
        return postJdbc.deleteAPost(id);
    }


}


