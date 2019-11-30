package com.articles.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Article {

    @Id
    private String id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column
    private String body;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="article_tag",
    joinColumns = { @JoinColumn(name="article_id")},
    inverseJoinColumns = { @JoinColumn(name="tag_name")})
    private Set<Tag> tags = new HashSet<>();

    public Article() {
        this.createdDate = new Date();
    }

    public Article(String id, String title, Date date, String body, Set<Tag> tags) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.body = body;
        this.tags = tags;
        this.createdDate = new Date();
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
