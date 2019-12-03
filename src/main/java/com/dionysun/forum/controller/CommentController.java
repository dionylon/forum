package com.dionysun.forum.controller;


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
}
