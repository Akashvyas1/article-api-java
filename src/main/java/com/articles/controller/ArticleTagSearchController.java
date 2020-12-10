package com.articles.controller;

import com.articles.dto.ArticleTagSearchResult;
import com.articles.service.ArticleSearchService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(path = "/tags")
public class ArticleTagSearchController {

    private final ArticleSearchService articleSearchService;

    public ArticleTagSearchController(final ArticleSearchService articleSearchService) {
        this.articleSearchService = articleSearchService;
    }

    @GetMapping(path = "/{tagName}/{date}", produces = "application/json")
    public ArticleTagSearchResult searchArticlesByTagAndDate(
            @PathVariable("tagName") final String tagName,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyyMMdd") @Valid final Date date) {
        return articleSearchService.searchArticlesByTagAndDate(tagName, date);
    }

}
