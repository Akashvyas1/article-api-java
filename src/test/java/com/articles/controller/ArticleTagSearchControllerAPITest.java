package com.articles.controller;

import com.articles.ArticlesAPIApplication;
import com.articles.dao.ArticleDao;
import com.articles.model.Article;
import com.articles.model.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ArticlesAPIApplication.class)
@ActiveProfiles("h2")
@AutoConfigureMockMvc
public class ArticleTagSearchControllerAPITest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleDao articleDao;

    @Test
    public void searchArticlesByTagAndDate_InvalidDate() throws Exception {
        mvc.perform(get("/tags/health/20191330"))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void searchArticlesByTagAndDate() throws Exception {
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

        Article article1 = new Article("1", "title1", new Date(), "body1", tags1);
        Article article2 = new Article("2", "title2", new Date(), "body2", tags2);

        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);

        Mockito.when(articleDao.
                findArticlesByTagsContainsAndDateOrderByCreatedDateDesc(any(), any())).thenReturn(articles);

        mvc.perform(get("/tags/health/20191130"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(2)))
                .andExpect(jsonPath("$.related_tags", hasSize(3)));
    }

    @Test
    public void searchArticlesByTagAndDate_NoResultsFound() throws Exception {
        mvc.perform(get("/tags/health/20191130"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(0)));

    }
}
