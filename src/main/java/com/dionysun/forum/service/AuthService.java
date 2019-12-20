package com.dionysun.forum.service;

import com.dionysun.forum.dao.UserDao;
import com.dionysun.forum.entity.User;
import com.dionysun.forum.util.JwtTokenUtil;
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
    @Autowired
    JwtTokenUtil jwtTokenUtil;

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

    /**
     * 检验token中的id与目标id是否一致
     * @param token jwt token，payload为userId
     * @param userId 目标id
     * @return ==
     */
    public boolean checkTokenId(String token, long userId){
        Long tokenId = null;
        try {
            tokenId = Long.parseLong(jwtTokenUtil.getUserIdFromToken(token));
        }catch (NumberFormatException e){
            e.printStackTrace();
            return false;
        }
        return Objects.equals(tokenId, userId);
    }
}
