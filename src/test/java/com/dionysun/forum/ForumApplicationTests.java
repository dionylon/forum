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
    @Autowired
    private ThumbUpDao likeDao;

    @Test
    public void likeTest(){
        for(int i = 1; i <= 10; ++i) {
            ThumbUp thumbUp = new ThumbUp();
            thumbUp.setUserId((long) i);
            thumbUp.setType(1);
            thumbUp.setLikeTime(new Date());
            thumbUp.setThumbId((long) i);
            likeDao.save(thumbUp);
        }
    }

}
