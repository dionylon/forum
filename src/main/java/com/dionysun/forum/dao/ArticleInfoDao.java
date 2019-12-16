package com.dionysun.forum.dao;

import com.dionysun.forum.entity.ArticleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ArticleInfoDao extends JpaRepository<ArticleInfo, Long> {
    List<ArticleInfo> findArticleInfoByAuthorId(Long authorId);
    List<ArticleInfo> findArticleInfosByCreateTimeAfter(Date date);
}
