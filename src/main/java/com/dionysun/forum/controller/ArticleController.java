package com.dionysun.forum.controller;

import com.alibaba.fastjson.JSONObject;
import com.dionysun.forum.common.Result;
import com.dionysun.forum.common.StatusCode;
import com.dionysun.forum.entity.Article;
import com.dionysun.forum.entity.ThumbUpType;
import com.dionysun.forum.service.ArticleService;
import com.dionysun.forum.service.CommentService;
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
    @Autowired
    private CommentService commentService;

    /**
     * 发布文章
     * @param body 文章信息，包括用户id和文章标题、内容
     * @return 发布成功，返回消息和文章的id
     */
    @PostMapping("")
    public Result uploadArticle(@RequestBody JSONObject body){
        articleService.uploadArticle(body);
        return new Result(StatusCode.OK,"发布成功");
    }


    /**
     * 点赞/踩，已经点赞则取消点赞（点踩），否则点赞
     * @param userId id
     * @param articleId id
     * @return 结果,OK说明点赞，REPEAT说明点过赞了，点踩
     */
    @PostMapping("/thumbUp/{userId}/{articleId}")
    public Result thumbUpArticle(@PathVariable(value = "userId")long userId,
                          @PathVariable(value = "articleId")long articleId){
        if(!articleService.isThumbUp(userId,articleId, ThumbUpType.ARTICLE)){
            articleService.thumbUp(userId,articleId,ThumbUpType.ARTICLE);
            return new Result(StatusCode.OK,"点赞成功");
        }
        articleService.thumbDown(userId,articleId,ThumbUpType.ARTICLE);
        return new Result(StatusCode.REPEAT, "取消点赞");
    }
    @GetMapping("/thumbUp/{userId}/{articleId}")
    public Result isThumbUpArticle(@PathVariable(value = "userId")long userId,
                            @PathVariable(value = "articleId")long articleId){
        Result result = new Result(StatusCode.OK, "点赞结果");
        result.put("isThumbUp",false);
        if(articleService.isThumbUp(userId,articleId,ThumbUpType.ARTICLE)) {
            result.put("isThumbUp", true);
            return result;
        }
        return result;
    }
    @GetMapping("/{articleId}")
    public ResponseEntity getArticleById(@PathVariable(value = "articleId")long articleId){
        Article article = articleService.getArticleById(articleId);
        if(article == null){
            return ResponseEntity.badRequest().build();
        }
        article.setThumbUp(articleService.countThumbUp(articleId));
        return ResponseEntity.ok().body(JSONObject.toJSON(article));
    }

    @GetMapping("/page/{page}/size/{size}")
    public ResponseEntity<Page<Article>> getArticlesPage(@PathVariable(value = "page") int page,
                                                         @PathVariable(value = "size") int size){
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
