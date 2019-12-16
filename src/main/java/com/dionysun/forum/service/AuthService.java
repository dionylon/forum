package com.dionysun.forum.service;

import com.dionysun.forum.dao.UserDao;
import com.dionysun.forum.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User login(String username, String password){
        User user = userDao.findUserByName(username);
        if(user == null || !isValidPassword(password, user.getPassword())){
            return null;
        }
        return user;
    }

    public void register(String username, String password){
        User user = new User();
        user.setName(username);
        user.setPassword(passwordEncoder.encode(password));
        userDao.save(user);
    }

    /**
     *  验证密码
     * @param rawPassword 未加密的密码值
     * @param encodePassword 加密后的密码值
     * @return 密码正确性
     */
    private boolean isValidPassword(String rawPassword, String encodePassword) {
        return passwordEncoder.matches(rawPassword, encodePassword);
    }
}
