package com.articles.service;

import com.articles.dao.ArticleDao;
import com.articles.dto.ArticleTagSearchResult;
import com.articles.model.Article;
import com.articles.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ArticleSearchService {

    private static final int MAX_RESULT = 10;

    @Autowired
    private ArticleDao articleDao;

    public ArticleTagSearchResult searchArticlesByTagAndDate(String tagName, Date date) {

        Tag searchTag = new Tag(tagName);
        List<Article> articles = articleDao.findArticlesByTagsContainsAndDateOrderByCreatedDateDesc(searchTag, date);

        ArticleTagSearchResult searchResult = new ArticleTagSearchResult();
        searchResult.setTag(tagName);
        searchResult.setCount(articles.size());

        int maxResults = MAX_RESULT < articles.size() ? MAX_RESULT: articles.size();
        Set<String> articleIds = IntStream.range(0, maxResults)
                .mapToObj(i -> articles.get(i).getId())
                .collect(Collectors.toSet());
        searchResult.setArticleIds(articleIds);

        searchResult.setRelatedTags(getRelatedTags(searchTag, articles));
        return searchResult;

    }

    private Set<String> getRelatedTags(Tag searchTag, List<Article> articles) {
/*        Set<String> relatedTags = new HashSet<>();
        for (Article article : articles) {
            for (Tag t : article.getTags()) {
                if (!t.equals(searchTag)) {
                    relatedTags.add(t.getName());
                }
            }

        }*/

        return articles.stream()
                .map(Article::getTags)
                .flatMap(Collection::stream)
                .filter(c -> !c.equals(searchTag))
                .map(Tag::getName)
                .collect(Collectors.toSet());


    }
}
