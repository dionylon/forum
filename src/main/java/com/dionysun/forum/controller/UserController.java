package com.dionysun.forum.controller;

import com.alibaba.fastjson.JSONObject;
import com.dionysun.forum.annotation.Auth;
import com.dionysun.forum.common.Result;
import com.dionysun.forum.common.StatusCode;
import com.dionysun.forum.entity.User;
import com.dionysun.forum.entity.UserInfo;
import com.dionysun.forum.service.AuthService;
import com.dionysun.forum.service.UserInfoService;
import com.dionysun.forum.service.UserService;
import com.dionysun.forum.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(value = "api/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @Auth
    @PostMapping("/{userId}")
    public Result updateUserInfo(@RequestBody JSONObject body,
                                 @PathVariable("userId") long userId,
                                 HttpServletRequest request){
        String token = request.getHeader("token");
        if(!authService.checkTokenId(token,userId)){
            return new Result(StatusCode.ERROR, "无权限修改");
        }
        String name = body.getString("userName");
        if(StringUtils.isEmpty(name)){
            return new Result(StatusCode.ERROR, "用户名不能为空");
        }
        UserInfo info = userInfoService.getUserInfoByName(name);
        if(info != null && info.getId() != userId){
            return new Result(StatusCode.ERROR, "用户名已存在");
        }
        if(!userService.updateUser(userId, body)){
            return new Result(StatusCode.ERROR, "用户不存在");
        }
        return new Result(StatusCode.OK, "修改成功");
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUserInfoById(@PathVariable(name = "userId")long userId){
        UserInfo userInfo = userInfoService.getUserInfoById(userId);
        if(userInfo == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(JSONObject.toJSON(userInfo));
    }
}
