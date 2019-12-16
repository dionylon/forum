package com.dionysun.forum.service;

import com.dionysun.forum.dao.ArticleDao;
import com.dionysun.forum.dao.CommentDao;
import com.dionysun.forum.dao.ThumbUpDao;
import com.dionysun.forum.dao.UserInfoDao;
import com.dionysun.forum.entity.Comment;
import com.dionysun.forum.entity.ThumbUpType;
import com.dionysun.forum.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ThumbUpDao thumbUpDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private ArticleDao articleDao;

    @Transactional
    public boolean addComment(long userId, long articleId, String content){
        if(!userInfoDao.existsById(userId) || !articleDao.existsById(articleId)){
            return false;
        }
        Comment comment = new Comment();
        Date now = new Date();
        comment.setAuthorId(userId);
        comment.setArticleId(articleId);
        comment.setCreateTime(now);
        comment.setThumbUp(0);
        comment.setContent(content);
        commentDao.save(comment);
        return true;
    }

    /**
     * 通过文章id查找评论，评论的点赞数通过点赞表计算出
     * @param articleId 文章id
     * @return 评论的List
     */
    public List<Comment> getCommentsByArticleId(long articleId){
        List<Comment> comments =  commentDao.findCommentsByArticleId(articleId);
        for (Comment comment : comments){
            int thumbUps = thumbUpDao.countThumbUpsByThumbIdAndType(comment.getId(), ThumbUpType.COMMENT);
            UserInfo authorInfo = userInfoDao.findUserInfoById(comment.getAuthorId());
            comment.setAuthorInfo(authorInfo);
            comment.setThumbUp(thumbUps);
        }
        return comments;
    }
}
