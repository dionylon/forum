package com.dionysun.forum.interceptor;

import com.dionysun.forum.annotation.Auth;
import com.dionysun.forum.annotation.Pass;
import com.dionysun.forum.dao.UserDao;
import com.dionysun.forum.entity.User;
import com.dionysun.forum.service.UserInfoService;
import com.dionysun.forum.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserInfoService userService;
    @Autowired
    private UserDao userDao;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        //检查是否有pass注释，有则跳过认证
        if (method.isAnnotationPresent(Pass.class)) {
            Pass passToken = method.getAnnotation(Pass.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(Auth.class)) {
            Auth auth = method.getAnnotation(Auth.class);
            if (auth.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                long userId;
                userId = Long.parseLong(jwtTokenUtil.getUserIdFromToken(token));
                User user = userDao.getOne(userId);
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                // 验证 token
                return jwtTokenUtil.validateToken(token, user);
            }
        }
        return true;
    }
}
