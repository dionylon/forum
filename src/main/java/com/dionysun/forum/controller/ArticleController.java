package com.dionysun.forum.controller;

import com.alibaba.fastjson.JSONObject;
import com.dionysun.forum.entity.Article;
import com.dionysun.forum.entity.Comment;
import com.dionysun.forum.service.ArticleService;
import com.dionysun.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;

    @GetMapping("")
    public ResponseEntity<Page<Article>> getArticlesPage(@RequestParam(value = "page",defaultValue = "1") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size){
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

    @PostMapping("/comment")
    public ResponseEntity<Object> addComment(@RequestBody JSONObject params){
        commentService.addComment(params.getLong("authorId"),params.getLong("articleId"),params.getString("content"));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
