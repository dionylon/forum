package com.dionysun.forum.dao;

import com.dionysun.forum.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CommentDao extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByArticleId(Long id);
}
