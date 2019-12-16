package com.dionysun.forum.service;

import com.dionysun.forum.dao.FollowDao;
import com.dionysun.forum.dao.UserInfoDao;
import com.dionysun.forum.entity.Follow;
import com.dionysun.forum.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private FollowDao followDao;

    public List<UserInfo> getFansById(long id){
        List<Follow> followList = followDao.findAllByUserId(id);
        List<Long> followIdList = new ArrayList<>();
        for(int i = 0; i < followList.size(); ++i){
            followIdList.add(followList.get(i).getUserId());
        }
        return userInfoDao.findAllById(followIdList);
    }
    public boolean exists(String username){
        return userInfoDao.existsByName(username);
    }
    public UserInfo getUserInfoById(long id){
        return userInfoDao.getOne(id);
    }
}
