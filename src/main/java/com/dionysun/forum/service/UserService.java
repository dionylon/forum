package com.dionysun.forum.service;

import com.alibaba.fastjson.JSONObject;
import com.dionysun.forum.dao.UserDao;
import com.dionysun.forum.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean updateUser(long userId, JSONObject body){
        if(!userDao.existsById(userId)){
            return false;
        }
        User user = userDao.getOne(userId);
        int gender = body.getInteger("gender");
        Long dayTime = body.getLong("birthday");
        if(dayTime != null) {
            Date birthday = new Date(dayTime);
            user.setBirthday(birthday);
            System.out.println(dayTime + " "+ birthday.getTime());
        }
        user.setName(body.getString("userName"));

        user.setGender(String.valueOf(gender));
        userDao.save(user);
        return true;
    }
}
