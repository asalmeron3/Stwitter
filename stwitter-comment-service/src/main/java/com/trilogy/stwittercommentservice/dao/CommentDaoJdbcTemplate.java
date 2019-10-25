package com.trilogy.stwittercommentservice.dao;

import com.netflix.discovery.converters.Auto;
import com.trilogy.stwittercommentservice.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentDaoJdbcTemplate implements CommentDao {

    public static final String FIND_ALL_COMMENTS = "select * from comment";
    public static final String FIND_ALL_COMMENTS_BY_POST_ID = "select * from comment where post_id = ?";
    public static final String GET_COMMENT_BY_ID = "select * from comment where comment_id = ?";
    public static final String MAKE_A_COMMENT = "insert into comment (post_id, create_date, commenter_name, comment) " +
            "values (?, ?, ?, ?)";
    public static final String UPDATE_A_COMMENT = "update comment set post_id = ?, create_date = ?, " +
            "commenter_name = ?, comment = ? where comment_id = ?";
    public static final String DELETE_A_COMMENT = "delete from comment where comment_id = ?";

    private JdbcTemplate commentJdbcTemplate;

    @Autowired
    public CommentDaoJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.commentJdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> findAllComments() {
        List<Comment> comments = commentJdbcTemplate.query(FIND_ALL_COMMENTS, this::mapToComment);
        return comments;
    }

    @Override
    public List<Comment> findAllCommentsByPostId(int postId) {
        List<Comment> comments = commentJdbcTemplate.query(FIND_ALL_COMMENTS_BY_POST_ID, this::mapToComment, postId);
        return comments;
    }

    @Override
    public Comment makeAComment(Comment comment) {
        commentJdbcTemplate.update(MAKE_A_COMMENT,
                comment.getPostId(),
                comment.getDateCreated(),
                comment.getCommenterName(),
                comment.getComment());

        int id = commentJdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        comment.setCommentId(id);

        return comment;
    }

    @Override
    public Comment getCommentById(int commentId) {
        try {
            Comment comment = commentJdbcTemplate.queryForObject(GET_COMMENT_BY_ID, this::mapToComment, commentId);
            return comment;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public Comment updateComment(Comment comment) {
        int rowsUpdated = commentJdbcTemplate.update(UPDATE_A_COMMENT,
                comment.getPostId(),
                comment.getDateCreated(),
                comment.getCommenterName(),
                comment.getComment(),
                comment.getCommentId());

        return rowsUpdated == 1 ? comment : null;
    }

    @Override
    public String deleteAComment(int id) {
        String deletionFailedResponse =  "No comment with comment_id " + id + " was found. No comment deleted.";
        String deletionSuccessfulResponse = "This comment has been deleted.";

        int rowsDeleted = commentJdbcTemplate.update(DELETE_A_COMMENT, id);

        return rowsDeleted == 1 ? deletionSuccessfulResponse : deletionFailedResponse;
    }

    public Comment mapToComment(ResultSet rs, int rowNum) throws SQLException {
        Comment comment = new Comment(
                rs.getInt("post_id"),
                rs.getString("commenter_name"),
                rs.getDate("create_date").toLocalDate(),
                rs.getString("comment")
        );
        comment.setCommentId(rs.getInt("comment_id"));

        return comment;
    }
}
