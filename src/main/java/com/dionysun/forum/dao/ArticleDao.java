package com.dionysun.forum.dao;

import com.dionysun.forum.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<Article, Long> {
    List<Article> findArticleByAuthorId(Long authorId);
    List<Article> findArticlesByCreateTimeAfter(Date date);
}
