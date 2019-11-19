package com.dionysun.forum.controller;

import com.dionysun.forum.entity.Article;
import com.dionysun.forum.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("")
    public ResponseEntity<Page<Article>> getArticlesPage(@RequestParam(value = "page",defaultValue = "1") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size){
        System.out.println("page: " + page + " size: " + size);
        return ResponseEntity.ok().body(articleService.getArticles(page - 1 , size));
    }

    @GetMapping("/latest")
    public ResponseEntity latestArticles(){
        return ResponseEntity.ok().body(articleService.getLatestArticles());
    }

    @GetMapping("/latest/{days}")
    public ResponseEntity latestArticles(@PathVariable Integer days){
        return ResponseEntity.ok().body(articleService.getLatestArticles(days));
    }

}
