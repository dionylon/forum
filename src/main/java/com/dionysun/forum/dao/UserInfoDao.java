package com.dionysun.forum.dao;

import com.dionysun.forum.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoDao extends JpaRepository<UserInfo, Long> {
    UserInfo findUserInfoByName(String name);
    UserInfo findUserInfoById(long id);
    boolean existsByName(String name);
}
