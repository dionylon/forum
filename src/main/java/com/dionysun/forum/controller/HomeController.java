package com.dionysun.forum.controller;

import com.alibaba.fastjson.JSONObject;
import com.dionysun.forum.common.Constant;
import com.dionysun.forum.common.Result;
import com.dionysun.forum.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/test")
@Api("测试Api")
public class HomeController {
    @ApiOperation(value = "打个招呼吧",notes = "测试使用")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> hello(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","hello");
        jsonObject.put("status", Constant.OK);
        return ResponseEntity.ok(jsonObject);
    }

    @ApiOperation(value = "获取user", notes = "根据id获取user对象")
    @GetMapping(value = "/get/{userid}")
    public Result<User> getUser(@PathVariable Integer userid){
        User u = new User();
        u.setId(-100);
        u.setName("test");
        u.setGender("嬲");
        u.setPassword("1234");
        return new Result<>(Constant.OK, "获取成功", u);
    }
}
