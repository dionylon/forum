package com.dionysun.forum.controller;

import com.alibaba.fastjson.JSONObject;
import com.dionysun.forum.common.Result;
import com.dionysun.forum.common.StatusCode;
import com.dionysun.forum.entity.User;
import com.dionysun.forum.service.LoginService;
import com.dionysun.forum.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.dionysun.forum.common.StatusCode.ERROR;

@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
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
           return new Result(ERROR, "");
        }
        Result result = new Result(StatusCode.OK, "OK");
        User user = loginService.login(username, password);
        if(user == null){
            return new Result(ERROR, "错误的用户名/密码");
        }
        result.put("token",jwtTokenUtil.generateToken(user));
        return result;
    }
}
