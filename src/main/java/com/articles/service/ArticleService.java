package com.articles.service;

import com.articles.dao.ArticleDao;
import com.articles.dto.ArticleDTO;
import com.articles.model.Article;
import com.articles.model.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleDao articleDao;

    private static final Format DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    public ArticleService(final ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public void postArticle(final ArticleDTO articleDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        article.setDate(getFormattedDate(articleDTO.getDate()));
        Set<Tag> tags = articleDTO.getTags().stream()
                .map(Tag::new)
                .collect(Collectors.toSet());
        article.setTags(tags);
        articleDao.save(article);
    }

    public Optional<ArticleDTO> getArticle(final String articleId) {
        Optional<Article> articleFromDb = articleDao.findById(articleId);

        if (articleFromDb.isPresent()) {
            Article article = articleFromDb.get();
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(article, articleDTO);
            articleDTO.setDate(DATE_FORMATTER.format(article.getDate()));
            Set<String> tags = article.getTags().stream()
                    .map(Tag::getName)
                    .collect(Collectors.toSet());
            articleDTO.setTags(tags);
            return Optional.of(articleDTO);
        } else {
            return Optional.empty();
        }
    }

    private Date getFormattedDate(final String date) {
            return Date.from(LocalDate.parse(date).atStartOfDay()
                    .atZone(ZoneId.systemDefault()).toInstant());
    }
}
