package com.dionysun.forum.controller;

import com.alibaba.fastjson.JSONObject;
import com.dionysun.forum.annotation.Auth;
import com.dionysun.forum.entity.UserInfo;
import com.dionysun.forum.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "api/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/{userId}")
    public ResponseEntity getUserInfoById(@PathVariable(name = "userId")long userId){
        UserInfo userInfo = userInfoService.getUserInfoById(userId);
        if(userInfo == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(JSONObject.toJSON(userInfo));
    }
}
