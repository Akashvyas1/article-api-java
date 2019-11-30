package com.articles.controller;

import com.articles.dto.ArticleTagSearchResult;
import com.articles.service.ArticleSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(path = "/tags")
public class ArticleTagSearchController
{
    private static final Logger LOG = LoggerFactory.getLogger(ArticleTagSearchController.class);

    @Autowired
    private ArticleSearchService articleSearchService;

    @GetMapping(path="/{tagName}/{date}", produces = "application/json")
    public ArticleTagSearchResult searchArticlesByTagAndDate(@PathVariable("tagName") String tagName,
             @PathVariable("date") @DateTimeFormat(pattern = "yyyyMMdd") @Valid  Date date) {
        return articleSearchService.searchArticlesByTagAndDate(tagName, date);
    }

}
