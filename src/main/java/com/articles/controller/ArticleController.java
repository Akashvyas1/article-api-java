package com.articles.controller;

import com.articles.dto.ArticleDTO;
import com.articles.exception.InvalidClientRequestException;
import com.articles.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.Optional;

@RestController
@RequestMapping(path = "/articles")
@Validated
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(path = "/_health")
    public String checkStatus() {
        return "Normal";
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ArticleDTO getArticle(
            @PathVariable("id")
            @Pattern(message = "must be a number", regexp = "^[0-9]*$")
            @Valid final String id) {

        Optional<ArticleDTO> articleFound = articleService.getArticle(id);
        return articleFound.orElseThrow(
                () -> new InvalidClientRequestException("Article not found", HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public void postArticle(@Valid @RequestBody final ArticleDTO articleDTO) {
        articleService.postArticle(articleDTO);

    }
}
