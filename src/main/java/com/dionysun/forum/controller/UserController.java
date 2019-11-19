package com.dionysun.forum.controller;

import com.dionysun.forum.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "api/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;


}
