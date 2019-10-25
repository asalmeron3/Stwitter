package com.trilogy.stwitterservice.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

public class Post {

    private int post_id;

    @NotNull(message = "post_date must follow the pattern yyyy-mm-dd")
    private LocalDate post_date;

    @NotNull(message = "poster_name cannot be blank")
    @Size(max = 50, message = "poster_name cannot be longer than 50 characters")
    private String poster_name;

    @NotNull(message = "post cannot be blank")
    @Size(max = 255, message = "post cannot be longer than 255 characters")
    private String post;

    public Post() {
    }

    public Post(LocalDate post_date, String poster_name, String post) {
        this.post_date = post_date;
        this.poster_name = poster_name;
        this.post = post;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post1 = (Post) o;
        return post_id == post1.post_id &&
                Objects.equals(post_date, post1.post_date) &&
                Objects.equals(poster_name, post1.poster_name) &&
                Objects.equals(post, post1.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post_id, post_date, poster_name, post);
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", post_date=" + post_date +
                ", poster_name='" + poster_name + '\'' +
                ", post='" + post + '\'' +
                '}';
    }
}
