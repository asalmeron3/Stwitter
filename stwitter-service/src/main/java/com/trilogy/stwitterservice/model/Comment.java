package com.trilogy.stwitterservice.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

public class Comment {

    private int commentId;

    @NotNull(message = "This comment requires a numeric postId")
    private int postId;

    @NotEmpty(message = "This comment requires a commenterName")
    @Size(max = 50, message = "The commenterName cannot exceed 50 characters")
    private String commenterName;

    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "This comment must contain a date in the format yyyy-mm-dd")
    private LocalDate dateCreated;

    @NotEmpty(message = "This comment must contain content")
    @Size(max = 255, message = "The content in this comment can not exceed 255 characters")
    private String comment;

    public Comment() {
    }

    public Comment(int postId, String commenterName, LocalDate dateCreated, String comment) {
        this.postId = postId;
        this.commenterName = commenterName;
        this.dateCreated = dateCreated;
        this.comment = comment;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return commentId == comment1.commentId &&
                postId == comment1.postId &&
                Objects.equals(commenterName, comment1.commenterName) &&
                Objects.equals(dateCreated, comment1.dateCreated) &&
                Objects.equals(comment, comment1.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, postId, commenterName, dateCreated, comment);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", postId=" + postId +
                ", commenterName='" + commenterName + '\'' +
                ", dateCreated=" + dateCreated +
                ", comment='" + comment + '\'' +
                '}';
    }
}
