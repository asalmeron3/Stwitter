package com.trilogy.stwitterpostservice.dao;

import com.trilogy.stwitterpostservice.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostDaoJdbcTemplate implements PostDao {

    @Autowired
    private JdbcTemplate postJdbcTemplate;

    public PostDaoJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.postJdbcTemplate = jdbcTemplate;
    }

    private static final String FIND_ALL_POSTS = "select * from post";
    private static final String MAKE_A_POST = "insert into post (post_date, poster_name, post) values (?, ?, ?)";
    private static final String GET_A_POST_BY_ID = "select * from post where post_id = ?";
    private static final String GET_A_POST_BY_USER = "select * from post where poster_name = ?";
    private static final String UPDATE_POST =
            "update post set post_date = ?, poster_name = ?, post = ? where post_id = ?";
    private static final String DELETE_POST = "delete from post where post_id = ?";

    @Override
    public List<Post> findAllPosts() {
        List<Post> posts = postJdbcTemplate.query(FIND_ALL_POSTS, this::mapRowToPost);
        return posts;
    }

    @Override
    public List<Post> findAllPostsByPosterName(String poster_name) {
        List<Post> posts = postJdbcTemplate.query(GET_A_POST_BY_USER, this::mapRowToPost, poster_name);
        return posts;
    }

    @Override
    public Post makeAPost(Post post) {
        postJdbcTemplate.update(
            MAKE_A_POST,
            post.getPost_date(),
            post.getPoster_name(),
            post.getPost()
        );

        int id = postJdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        post.setPost_id(id);

        return post;
    }

    @Override
    public Post getPostById(int postId) {
        try {
            Post post = postJdbcTemplate.queryForObject(GET_A_POST_BY_ID, this::mapRowToPost, postId);
            return post;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public Post updatePost(Post post) {
        int rowsUpdated = postJdbcTemplate.update(
                UPDATE_POST,
                post.getPost_date(),
                post.getPoster_name(),
                post.getPost(),
                post.getPost_id()
        );

        return rowsUpdated == 1 ? post : null;
    }

    @Override
    public String deleteAPost(int id) {
        String deletionFailedResponse =  "No post with post_id " + id + " was found. No post deleted.";
        String deletionSuccessfulResponse = "Post with post_id " + id + " has been deleted.";

        int rowsDeleted = postJdbcTemplate.update(DELETE_POST, id);

        return rowsDeleted == 1 ? deletionSuccessfulResponse : deletionFailedResponse;
    }

    public Post mapRowToPost(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post (
                rs.getDate("post_date").toLocalDate(),
                rs.getString("poster_name"),
                rs.getString("post")
        );
        post.setPost_id(rs.getInt("post_id"));

        return post;
    }
}
