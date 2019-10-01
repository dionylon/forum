package com.dionysun.forum;

import com.dionysun.forum.dao.ArticleDao;
import com.dionysun.forum.dao.UserDao;
import com.dionysun.forum.entity.Article;
import com.dionysun.forum.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private ArticleDao articleDao;
    @Test
    public void findTest(){
        for(int i = 0; i < 20; ++i){
            Article article = new Article();
            article.setAuthorId(10001);
            article.setContent("test");
            article.setId(1000 + i);
            article.setVisible(1);
            article.setThumbUp(i*11);
            article.setGmtCreate(new Date());
            article.setGmtModified(new Date());
            articleDao.save(article);
        }
    }

    @Test
    public void listTest(){
        List<Article> articles = articleDao.findArticleByAuthorId(10001);
        if(articles != null){
            for(int i = 0; i < articles.size(); ++i){
                System.out.println(articles.get(i));
            }
        }
    }
}
