package com.dionysun.forum.dao;

import com.dionysun.forum.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByArticleId(Long id);
}
