package com.dionysun.forum.controller;


import com.alibaba.fastjson.JSONObject;
import com.dionysun.forum.annotation.Auth;
import com.dionysun.forum.common.Result;
import com.dionysun.forum.common.StatusCode;
import com.dionysun.forum.entity.Comment;
import com.dionysun.forum.service.CommentService;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/article/{articleId}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable(name = "articleId") long articleId){
        return ResponseEntity.ok().body(commentService.getCommentsByArticleId(articleId));
    }
    @Auth
    @PostMapping("/article/{articleId}/comment")
    public Result addComment(@PathVariable(name = "articleId")long articleId,
                             @RequestBody JSONObject body){
        long userId = body.getLong("userId");
        String content = body.getString("content");
        if(commentService.addComment(userId, articleId, content)){
            return new Result(StatusCode.OK, "评论成功!");
        } else {
            return new Result(StatusCode.ERROR, "评论失败");
        }
    }
}
