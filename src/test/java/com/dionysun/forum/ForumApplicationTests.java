package com.dionysun.forum;

import com.dionysun.forum.dao.UserDao;
import com.dionysun.forum.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForumApplicationTests {

    @Autowired
    private UserDao userDao;
    @Test
    public void daoTest() throws Exception {
        User user = new User();
        user.setId(10001);
        user.setPassword("1234");
        user.setName("dionys");
        user.setPhone("1000000");
        user.setGender("男");
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-DD");
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yy-MM-DD HH:mm:ss");
        user.setBirthday(sdf.parse("1999-07-14"));
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        userDao.save(user);
    }
}
