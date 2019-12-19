package com.dionysun.forum.controller;

import com.alibaba.fastjson.JSONObject;
import com.dionysun.forum.common.Result;
import com.dionysun.forum.common.StatusCode;
import com.dionysun.forum.entity.User;
import com.dionysun.forum.service.AuthService;
import com.dionysun.forum.service.UserInfoService;
import com.dionysun.forum.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.dionysun.forum.common.StatusCode.ERROR;
import static com.dionysun.forum.common.StatusCode.OK;

@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserInfoService userInfoService;
    /**
     * 登录成功之后返回token
     * @param params 输入json包括用户名和密码
     * @return 成功返回token，否则返回错误信息
     */
    @PostMapping("/login")
    public JSONObject login(@RequestBody JSONObject params){
        String username = params.getString("username");
        String password = params.getString("password");
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
           return new Result(ERROR, "用户名/密码不能为空!");
        }
        Result result = new Result(StatusCode.OK, "OK");
        if(!userInfoService.exists(username)){
            return new Result(ERROR,"用户不存在");
        }
        User user = authService.login(username, password);
        if(user == null){
            return new Result(ERROR, "错误的用户名/密码");
        }
        result.put("userId",user.getId());
        result.put("username",user.getName());
        result.put("token",jwtTokenUtil.generateToken(user));
        return result;
    }

    /**
     * 注册
     * @param body username ： 用户名， password： 密码
     * @return 注册结果信息
     */
    @PostMapping("/register")
    public Result register(@RequestBody JSONObject body){
        String username = body.getString("username");
        String password = body.getString("password");
        if(userInfoService.exists(username)){
            return new Result(ERROR,"用户名已存在!");
        }
        authService.register(username, password);
        return new Result(OK, "注册成功");
    }
}
