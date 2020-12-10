package com.articles.service;

import com.articles.dao.ArticleDao;
import com.articles.dto.ArticleTagSearchResult;
import com.articles.model.Article;
import com.articles.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ArticleSearchService {

    private static final int MAX_RESULT = 10;

    private final ArticleDao articleDao;

    public ArticleSearchService(final ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public ArticleTagSearchResult searchArticlesByTagAndDate(final String tagName, final Date date) {

        Tag searchTag = new Tag(tagName);

        // Search articles by Tag and Date, in descending order of created datetime
        List<Article> articles = articleDao
                .findArticlesByTagsContainsAndDateOrderByCreatedDateDesc(searchTag, date);

        Set<String> articleIds = articles.stream()
                .limit(MAX_RESULT)
                .map(Article::getId)
                .collect(Collectors.toSet());

        ArticleTagSearchResult searchResult = new ArticleTagSearchResult();
        searchResult.setTag(tagName);
        searchResult.setCount(articles.size());
        searchResult.setArticleIds(articleIds);
        searchResult.setRelatedTags(getRelatedTags(searchTag, articles));
        return searchResult;
    }

    private Set<String> getRelatedTags(final Tag searchTag, final List<Article> articles) {

        return articles.stream()
                .map(Article::getTags)
                .flatMap(Collection::stream)
                .filter(c -> !c.equals(searchTag))
                .map(Tag::getName)
                .collect(Collectors.toSet());
    }
}
