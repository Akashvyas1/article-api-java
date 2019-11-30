package com.articles.dao;

import com.articles.model.Article;
import com.articles.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<Article, String> {
    List<Article> findArticlesByTagsContainsAndDateOrderByCreatedDateDesc(Tag tag, Date date);
}
