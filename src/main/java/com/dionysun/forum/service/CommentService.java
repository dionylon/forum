package com.dionysun.forum.service;

import com.dionysun.forum.dao.CommentDao;
import com.dionysun.forum.dao.ThumbUpDao;
import com.dionysun.forum.entity.Comment;
import com.dionysun.forum.entity.ThumbUpType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ThumbUpDao thumbUpDao;

    /**
     * 通过文章id查找评论，评论的点赞数通过点赞表计算出
     * @param articleId 文章id
     * @return 评论的List
     */
    public List<Comment> getCommentsByArticleId(long articleId){
        List<Comment> comments =  commentDao.findCommentsByArticleId(articleId);
        for (Comment comment : comments){
            int thumbUps = thumbUpDao.countThumbUpsByThumbIdAndType(comment.getId(), ThumbUpType.COMMENT);
            comment.setThumbUp(thumbUps);
        }
        return comments;
    }
}
