package com.articles.service;

import com.articles.dao.ArticleDao;
import com.articles.dto.ArticleTagSearchResult;
import com.articles.model.Article;
import com.articles.model.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ArticleSearchServiceTest {

    @InjectMocks
    private ArticleSearchService articleSearchService;

    @Mock
    private ArticleDao articleDao;

    @Test
    public void searchArticlesByTagAndDate() {
        // GIVEN (Mocks)
        Set<Tag> tags1 = new HashSet<>();
        final Tag health = new Tag("health");
        tags1.add(health);
        final Tag science = new Tag("science");
        tags1.add(science);

        Set<Tag> tags2 = new HashSet<>();
        tags2.add(health);
        final Tag fitness = new Tag("fitness");
        tags2.add(fitness);
        Tag body = new Tag("body");
        tags2.add(body);
        tags2.add(science);

        Article article1 = new Article("1", "title1", new Date(), "body1", tags1);
        Article article2 = new Article("2", "title2", new Date(), "body2", tags2);

        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);

        Mockito.when(articleDao.
                findArticlesByTagsContainsAndDateOrderByCreatedDateDesc(any(), any())).thenReturn(articles);
        // WHEN
        ArticleTagSearchResult searchResult = articleSearchService.searchArticlesByTagAndDate(
                "health", new Date());

        // THEN
        assertTrue(searchResult.getArticleIds().contains("1"));
        assertEquals(2, searchResult.getCount());
        assertEquals(2, searchResult.getArticleIds().size());
        assertEquals(3, searchResult.getRelatedTags().size());
        assertTrue(!searchResult.getRelatedTags().contains("health"));

    }

    @Test
    public void searchArticlesByTagAndDate_NoResultFound() {
        ArticleTagSearchResult searchResult = articleSearchService.searchArticlesByTagAndDate(
                "health", new Date());
        assertEquals(0, searchResult.getCount());
        assertTrue(searchResult.getArticleIds().isEmpty());

    }


}