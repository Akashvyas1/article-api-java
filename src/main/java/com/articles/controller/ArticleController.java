package com.articles.controller;

import com.articles.dto.ArticleDTO;
import com.articles.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping(path="/_health")
    public String checkStatus()
    {
        return "Normal";
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ArticleDTO getArticle(@PathVariable("id") String id) {
        return articleService.getArticle(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public void postArticle(@Valid @RequestBody ArticleDTO articleDTO) {
        articleService.postArticle(articleDTO);

    }
}
