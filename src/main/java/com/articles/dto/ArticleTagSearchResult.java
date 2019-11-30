package com.articles.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class ArticleTagSearchResult {

    private String tag;
    private int count;
    @JsonProperty("articles")
    private Set<String> articleIds;
    @JsonProperty("related_tags")
    private Set<String> relatedTags;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<String> getArticleIds() {
        return articleIds;
    }

    public void setArticleIds(Set<String> articleIds) {
        this.articleIds = articleIds;
    }

    public Set<String> getRelatedTags() {
        return relatedTags;
    }

    public void setRelatedTags(Set<String> relatedTags) {
        this.relatedTags = relatedTags;
    }
}
