package com.dionysun.forum;

import com.dionysun.forum.dao.*;
import com.dionysun.forum.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForumApplicationTests {

    @Autowired
    private UserDao userDao;
    @Autowired
    private FollowDao followDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CommentDao commentDao;

    public void followTest(){
        for(long i = 2;i < 10;++i){
            User user = null;
            Follow follow = new Follow();
            Optional<User> o =  userDao.findById(i);
            if(o.isPresent()){
                follow.setUserId(1L);
                follow.setFollowerId(i);
                followDao.save(follow);
            }
        }
    }

    public void articleTest(){
        for(int i = 2;i < 10;++i){
            Article article = new Article();
            article.setAuthorId((long) i);
            article.setThumbUp(i*i);
            article.setTitle(UUID.randomUUID().toString());
            article.setContent("test");
            article.setCreateTime(new Date());
            article.setLastModified(article.getCreateTime());
            articleDao.save(article);
        }
    }

    public void  commentTest(){
        for(int i = 1; i < 9; ++i){
            Comment comment = new Comment();
            comment.setArticleId((long) i);
            comment.setAuthorId(1L);
            comment.setContent(i % 2 == 0 ? "666" : "我觉得不行");
            comment.setCreateTime(new Date());
            commentDao.save(comment);
        }
    }
    @Autowired
    private UserInfoDao userInfoDao;

    public void userInfoTest(){
        UserInfo info = userInfoDao.findUserInfoById(1L);
        System.out.println(info);
    }
}
