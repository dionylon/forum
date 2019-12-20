package com.dionysun.forum.controller;

import com.alibaba.fastjson.JSONObject;
import com.dionysun.forum.annotation.Auth;
import com.dionysun.forum.common.Result;
import com.dionysun.forum.common.StatusCode;
import com.dionysun.forum.entity.Article;
import com.dionysun.forum.entity.ArticleInfo;
import com.dionysun.forum.entity.ThumbUpType;
import com.dionysun.forum.entity.User;
import com.dionysun.forum.service.ArticleService;
import com.dionysun.forum.service.AuthService;
import com.dionysun.forum.service.CommentService;
import com.dionysun.forum.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AuthService authService;
    /**
     * 发布文章
     * @param body 文章信息，包括用户id和文章标题、内容
     * @return 发布成功，返回消息和文章的id
     */
    @Auth
    @PostMapping("")
    public Result uploadArticle(@RequestBody JSONObject body){
        articleService.uploadArticle(body);
        return new Result(StatusCode.OK,"发布成功");
    }

    /**
     * 删除文章
     * @param articleId 文章id
     * @param request req
     * @return 信息
     */
    @Auth
    @DeleteMapping("/{articleId}")
    public Result deleteArticle(@PathVariable long articleId,
                                HttpServletRequest request){
        String token = request.getHeader("token");
        if(!articleService.existsById(articleId)){
            return new Result(StatusCode.ERROR, "文章不存在");
        }
        ArticleInfo articleInfo = articleService.getArticleInfoById(articleId);
        long authorId = articleInfo.getAuthorId();
        if(!authService.checkTokenId(token,authorId)){
            return new Result(StatusCode.ERROR, "无权限");
        }
        articleService.deleteById(articleId);
        return new Result(StatusCode.OK, "删除成功");
    }

    /**
     * 修改文章
     * @param body 文章
     * @param articleId 文章id
     * @param request req
     * @return res
     */
    @PostMapping("/{articleId}")
    public Result updateArticle(@RequestBody JSONObject body,
                                @PathVariable long articleId,
                                HttpServletRequest request){
        String token = request.getHeader("token");
        if(!articleService.existsById(articleId)){
            return new Result(StatusCode.ERROR, "文章不存在");
        }
        Article article = articleService.getArticleById(articleId);
        if(!authService.checkTokenId(token,article.getAuthorId())){
            return new Result(StatusCode.ERROR, "无权限修改");
        }
        String title = body.getString("title");
        String content = body.getString("content");
        if(StringUtils.isEmpty(title)){
            return new Result(StatusCode.ERROR, "标题不能为空");
        }
        if(StringUtils.isEmpty(content)){
            return new Result(StatusCode.ERROR, "内容不能为空");
        }
        article.setContent(content);
        article.setTitle(title);
        articleService.updateArticle(article);
        return new Result(StatusCode.OK, "修改成功");
    }
    /**
     * 点赞/踩，已经点赞则取消点赞（点踩），否则点赞
     * @param userId id
     * @param articleId id
     * @return 结果,OK说明点赞，REPEAT说明点过赞了，点踩
     */
    @Auth
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
        ArticleInfo article = articleService.getArticleInfoById(articleId);
        if(article == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(JSONObject.toJSON(article));
    }

    @GetMapping("/page/{page}/size/{size}")
    public ResponseEntity<Page<ArticleInfo>> getArticlesPage(@PathVariable(value = "page") int page,
                                                         @PathVariable(value = "size") int size){
        return ResponseEntity.ok().body(articleService.getArticles(page - 1 , size));
    }
    @GetMapping("/author/{authorId}")
    public ResponseEntity getArticlesByAuthorId(@PathVariable("authorId") long authorId){
        return ResponseEntity.ok().body(articleService.getArticlesByAuthorIdDesc(authorId));
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
