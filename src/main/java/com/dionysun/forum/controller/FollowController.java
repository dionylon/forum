package com.dionysun.forum.controller;


import com.alibaba.fastjson.JSONObject;
import com.dionysun.forum.entity.UserInfo;
import com.dionysun.forum.service.FollowService;
import com.dionysun.forum.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class FollowController {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    FollowService followService;

    @GetMapping("/fans/{userId}")
    public ResponseEntity getFans(@PathVariable("userId")long userId){
        List<UserInfo> followerList = followService.getFollowersById(userId);
        if(followerList == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(JSONObject.toJSON(followerList));
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity getFollows(@PathVariable("userId")long userId){
        List<UserInfo> followerList = followService.getFollowsById(userId);
        if(followerList == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(JSONObject.toJSON(followerList));
    }

}
