package com.dionysun.forum.service;

import com.dionysun.forum.dao.FollowDao;
import com.dionysun.forum.dao.UserDao;
import com.dionysun.forum.dao.UserInfoDao;
import com.dionysun.forum.entity.Follow;
import com.dionysun.forum.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowService {
    @Autowired
    FollowDao followDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserInfoDao userInfoDao;

    /**
     * 获取粉丝列表
     * @param userId id
     * @return 粉丝列表，用户不存在则返回null
     */
    public List<UserInfo> getFollowersById(long userId){
        if(!userDao.existsById(userId)){
            return null;
        }
        List<Follow> followList = followDao.findAllByUserId(userId);
        List<UserInfo> followerList = new ArrayList<>();
        for(Follow f : followList){
            followerList.add(userInfoDao.findUserInfoById(f.getFollowerId()));
        }
        return followerList;
    }

    /**
     * 获取关注了那些人
     * @param userId id
     * @return 关注列表
     */
    public List<UserInfo> getFollowsById(long userId){
        if(!userDao.existsById(userId)){
            return null;
        }
        List<Follow> followList = followDao.findAllByFollowerId(userId);
        List<UserInfo> followsList = new ArrayList<>();
        for(Follow f : followList){
            followsList.add(userInfoDao.findUserInfoById(f.getUserId()));
        }
        return followsList;
    }
}
