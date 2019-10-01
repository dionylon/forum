package com.dionysun.forum.dao;

import com.dionysun.forum.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<Article, Integer> {
    List<Article> findArticleByAuthorId(Integer authorId);
}
