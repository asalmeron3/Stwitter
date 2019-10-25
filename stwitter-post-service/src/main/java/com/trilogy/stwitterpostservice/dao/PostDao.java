package com.trilogy.stwitterpostservice.dao;

import com.trilogy.stwitterpostservice.model.Post;

import java.util.List;

public interface PostDao {

    List<Post> findAllPosts();
    List<Post> findAllPostsByPosterName(String poster_name);

    Post makeAPost(Post post);
    Post getPostById(int postId);
    Post updatePost(Post post);

    String deleteAPost(int id);
}
