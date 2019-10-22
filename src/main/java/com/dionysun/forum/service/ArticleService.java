package com.dionysun.forum.service;

import com.dionysun.forum.dao.ArticleDao;
import com.dionysun.forum.entity.Article;
import com.dionysun.forum.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    public Page<Article> getArticles(int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"createTime");
        return articleDao.findAll(pageable);
    }

    public List<Article> getArticlesByAuthorId(long id){
        return articleDao.findArticleByAuthorId(id);
    }

    public List<Article> getLatestArticles(){
        return getLatestArticles(1);
    }
    public List<Article> getLatestArticles(int days){
        return articleDao.findArticlesByCreateTimeAfter(DateUtil.daysBefore(days));
    }

}
