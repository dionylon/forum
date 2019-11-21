package com.dionysun.forum.service;

import com.dionysun.forum.dao.UserDao;
import com.dionysun.forum.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginService {
    @Autowired
    private UserDao userDao;

    public User login(String username, String password){
        User user = userDao.findUserByName(username);
        if(user == null || !isValidPassword(password, user.getPassword())){
            return null;
        }
        return user;
    }

    //TODO 使用哈希比较而不是直接比较
    private boolean isValidPassword(String oldStr, String newStr) {
        return Objects.equals(oldStr, newStr);
    }
}
