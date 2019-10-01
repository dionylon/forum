package com.dionysun.forum.service;

import com.dionysun.forum.dao.ArticleDao;
import com.dionysun.forum.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;

    public List<Article> getArticlesByAuthorId(int id){
        return articleDao.findArticleByAuthorId(id);
    }

}
