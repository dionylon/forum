package com.dionysun.forum.controller;

import com.dionysun.forum.common.Constant;
import com.dionysun.forum.common.Result;
import com.dionysun.forum.entity.Article;
import com.dionysun.forum.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("")
    public Result<Page<Article>> getArticlesPage(@RequestParam(value = "page",defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size){
        return new Result<>(Constant.OK,articleService.getArticles(page-1,size));
    }

    @GetMapping("/latest")
    public Result latestArticles(){
        return new Result<>(Constant.OK, articleService.getLatestArticles());
    }

    @GetMapping("/latest/{days}")
    public Result latestArticles(@PathVariable int days){
        return new Result<>(Constant.OK, articleService.getLatestArticles(days));
    }

}
