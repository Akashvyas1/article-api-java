package com.articles.service;

import com.articles.dao.ArticleDao;
import com.articles.dto.ArticleDTO;
import com.articles.exception.InvalidClientRequestException;
import com.articles.model.Article;
import com.articles.model.Tag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    ArticleDao articleDao;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldPersistArticle() {
        String[] tags = {"health", "science"};
        ArticleDTO articleDTO = new ArticleDTO("1", "title","2019-11-30","body",
                new HashSet<>(Arrays.asList(tags)));
        articleService.postArticle(articleDTO);
        Mockito.verify(articleDao).save(any());
    }

    @Test(expected = InvalidClientRequestException.class)
    public void shouldThrowExceptionForInvalidDateFormat() {
        String[] tags = {"health", "science"};
        ArticleDTO articleDTO = new ArticleDTO("1", "title","2019-11","body",
                new HashSet<>(Arrays.asList(tags)));
        articleService.postArticle(articleDTO);
    }

    @Test
    public void shouldGetArticle() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("health"));
        tags.add(new Tag("science"));
        Date date = new Date();
        Article article = new Article("1", "title1", date,"body", tags);
        Mockito.when(articleDao.findById("1")).thenReturn(Optional.of(article));
        ArticleDTO articleDTO = articleService.getArticle("1");
        assertEquals("title1", articleDTO.getTitle());
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(formatter.format(date), articleDTO.getDate());
        assertTrue(articleDTO.getTags().contains("science"));
    }

    @Test(expected = InvalidClientRequestException.class)
    public void shouldThrowExceptionIfNotFound() {
        articleService.getArticle("50");
    }
}