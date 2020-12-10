package com.articles.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;

public class ArticleDTO {

    @NotEmpty
    @Pattern(message = "must be a number", regexp="^[0-9]*$")
    private String id;

    @NotEmpty
    private String title;
    @NotEmpty
    @Pattern(message = "invalid date", regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    private String date;
    private String body;
    @NotEmpty
    private Set<String> tags;

    public ArticleDTO(String id, String title, String date, String body, Set<String> tags) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.body = body;
        this.tags = tags;
    }

    public ArticleDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", body='" + body + '\'' +
                ", tags=" + tags +
                '}';
    }
}
