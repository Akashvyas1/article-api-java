package com.articles.service;

import com.articles.dao.ArticleDao;
import com.articles.dto.ArticleDTO;
import com.articles.exception.InvalidClientRequestException;
import com.articles.model.Article;
import com.articles.model.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    private static Format formatter = new SimpleDateFormat("yyyy-MM-dd");


    public void postArticle(ArticleDTO articleDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        article.setDate(getFormattedDate(articleDTO.getDate()));
        Set<Tag> tags = articleDTO.getTags().stream().map(Tag::new).collect(Collectors.toSet());
        article.setTags(tags);
        articleDao.save(article);
    }

    public ArticleDTO getArticle(String articleId) {
        Optional<Article> articleFromDb = articleDao.findById(articleId);

        if (articleFromDb.isPresent()) {
            Article article = articleFromDb.get();
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(article, articleDTO);
            articleDTO.setDate(formatter.format(article.getDate()));
            Set<String> tags = article.getTags().stream().map(Tag::getName).collect(Collectors.toSet());
            articleDTO.setTags(tags);
            return articleDTO;
        } else {
            throw new InvalidClientRequestException("Article not found", HttpStatus.NOT_FOUND);
        }
    }

    private Date getFormattedDate(String date) {
        try {
            return Date.from(LocalDate.parse(date).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException dpe) {
            throw new InvalidClientRequestException("Invalid date", HttpStatus.BAD_REQUEST);
        }
    }
}
