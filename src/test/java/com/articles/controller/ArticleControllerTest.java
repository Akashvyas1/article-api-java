package com.articles.controller;

import com.articles.ArticlesAPIApplication;
import com.articles.dao.ArticleDao;
import com.articles.model.Article;
import com.articles.model.Tag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ArticlesAPIApplication.class)
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Before
    public void setUp() throws Exception {
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleDao articleDao;

    @Test
    public void testHealthCheck() throws Exception {
        mvc.perform(get("/articles/_health")).andExpect(status().isOk());
    }

    @Test
    public void testPostArticle() throws Exception {

        String jsonArticle = "{\n" +
                "  \"id\": \"1\",\n" +
                "  \"title\": \"latest science shows that potato chips are better for you than sugar\",\n" +
                "  \"date\" : \"2016-09-22\",\n" +
                "  \"body\" : \"some text, potentially containing simple markup about how potato chips are great\",\n" +
                "  \"tags\" : [\"health\", \"fitness\", \"science\"]\n" +
                "}";

        mvc.perform(post("/articles").content(jsonArticle).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void testPostArticleBadRequestForMissingDate() throws Exception {

        String jsonArticle = "{\n" +
                "  \"id\": \"1\",\n" +
                "  \"title\": \"latest science shows that potato chips are better for you than sugar\",\n" +
                "  \"body\" : \"some text, potentially containing simple markup about how potato chips are great\",\n" +
                "  \"tags\" : [\"health\", \"fitness\", \"science\"]\n" +
                "}";

        mvc.perform(post("/articles").content(jsonArticle).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testGetArticle() throws Exception {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("health"));
        Article article = new Article("1", "title1", new Date(),"body", tags);
        Mockito.when(articleDao.findById("1")).thenReturn(Optional.of(article));
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("title1")))
                .andExpect(jsonPath("$.tags[0]", is("health")));
    }

    @Test
    public void testGetArticleNotfound() throws Exception {

        mvc.perform(get("/articles/100"))
                .andExpect(status().isNotFound());

    }
}