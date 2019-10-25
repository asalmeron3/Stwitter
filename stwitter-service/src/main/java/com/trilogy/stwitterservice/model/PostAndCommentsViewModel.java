package com.trilogy.stwitterservice.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostAndCommentsViewModel {

    private int post_id;

    @NotNull(message = "post_date must follow the pattern yyyy-mm-dd")
    private LocalDate post_date;

    @NotNull(message = "poster_name cannot be blank")
    @Size(max = 50, message = "poster_name cannot be longer than 50 characters")
    private String poster_name;

    @NotNull(message = "post cannot be blank")
    @Size(max = 255, message = "post cannot be longer than 255 characters")
    private String post;

    private List<@Valid Comment> comments = new ArrayList<>();

    public PostAndCommentsViewModel() {
    }

    public PostAndCommentsViewModel(int post_id, LocalDate post_date, String poster_name, String post, List<Comment> comments) {
        this.post_id = post_id;
        this.post_date = post_date;
        this.poster_name = poster_name;
        this.post = post;
        this.comments = comments;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public LocalDate getPost_date() {
        return post_date;
    }

    public void setPost_date(LocalDate post_date) {
        this.post_date = post_date;
    }

    public String getPoster_name() {
        return poster_name;
    }

    public void setPoster_name(String poster_name) {
        this.poster_name = poster_name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostAndCommentsViewModel that = (PostAndCommentsViewModel) o;
        return post_id == that.post_id &&
                Objects.equals(post_date, that.post_date) &&
                Objects.equals(poster_name, that.poster_name) &&
                Objects.equals(post, that.post) &&
                Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post_id, post_date, poster_name, post, comments);
    }

    @Override
    public String toString() {
        return "PostAndCommentsViewModel{" +
                "post_id=" + post_id +
                ", post_date=" + post_date +
                ", poster_name='" + poster_name + '\'' +
                ", post='" + post + '\'' +
                ", comments=" + comments +
                '}';
    }
}
