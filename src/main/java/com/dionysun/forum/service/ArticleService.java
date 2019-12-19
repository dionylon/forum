package com.dionysun.forum.service;

import com.alibaba.fastjson.JSONObject;
import com.dionysun.forum.dao.ArticleDao;
import com.dionysun.forum.dao.ArticleInfoDao;
import com.dionysun.forum.dao.ThumbUpDao;
import com.dionysun.forum.entity.Article;
import com.dionysun.forum.entity.ArticleInfo;
import com.dionysun.forum.entity.ThumbUp;
import com.dionysun.forum.entity.ThumbUpType;
import com.dionysun.forum.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleInfoDao articleInfoDao;
    @Autowired
    private ThumbUpDao thumbUpDao;

    public int countThumbUp(long articleId){
        return thumbUpDao.countThumbUpsByThumbIdAndType(articleId, ThumbUpType.ARTICLE);
    }

    public void uploadArticle(JSONObject body){
        System.out.println(body);
        Article article = new Article();
        article.setAuthorId(body.getLong("authorId"));
        article.setTitle(body.getString("title"));
        article.setContent(body.getString("content"));
        Date now = new Date();
        article.setLastModified(now);
        article.setCreateTime(now);
        articleDao.save(article);
    }

    /**
     * 点赞
     * @param userId 点赞人的id
     * @param thumbId 点赞对象的id
     * @param type 点赞兑现的类型
     * @return 已经点赞则返回false，否则返回true
     */
    @Transactional
    public boolean thumbUp(long userId, long thumbId, int type){
        ThumbUp thumbUp = new ThumbUp(userId, type, thumbId);
        if(isThumbUp(userId, thumbId, type)){
            return false;
        }
        thumbUpDao.save(thumbUp);
        return true;
    }

    /**
     * 点踩
     * @param userId 用户id
     * @param thumbId 点踩的id
     * @param type 类型
     * @return 成功true，失败（没有点赞）false
     */
    @Transactional
    public boolean thumbDown(long userId, long thumbId, int type) {
        if(isThumbUp(userId, thumbId, type)){
            thumbUpDao.deleteByUserIdAndThumbIdAndType(userId, thumbId, type);
            return true;
        }
        return false;
    }
    /**
     * 是否已经点赞过了
     */
    public boolean isThumbUp(long userId, long thumbId, int type){
        return thumbUpDao.countThumbUpsByUserIdAndThumbIdAndType(userId,thumbId,type) != 0;
    }

    /**
     * 分页获取文章
     * @param page 第几页,从0开始
     * @param size 每页文章数
     * @return Page对象
     */
    public Page<Article> getArticles(int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"createTime");
        return articleDao.findAll(pageable);
    }

    /**
     * 通过作者id查文章列表
     * @param id 作者id
     * @return 文章List
     */
    public List<ArticleInfo> getArticlesByAuthorId(long id){
        return articleInfoDao.findArticleInfoByAuthorId(id);
    }

    /**
     * 时间降序
     * @param id
     * @return
     */
    public List<ArticleInfo> getArticlesByAuthorIdDesc(long id){
        return articleInfoDao.findArticleInfosByAuthorIdOrderByCreateTimeDesc(id);
    }

    /**
     * 获取最近3天的文章
     */
    public List<ArticleInfo> getLatestArticles(){
        return getLatestArticles(3);
    }

    /**
     * @param days 最近days天
     * @return 文章list
     */
    public List<ArticleInfo> getLatestArticles(int days){
        return articleInfoDao.findArticleInfosByCreateTimeAfter(DateUtil.daysBefore(days));
    }

    /**
     * 通过文章id获取文章
     * @param articleId id
     * @return 文章对象
     */
    public ArticleInfo getArticleInfoById(long articleId){
        return articleInfoDao.getOne(articleId);
    }


}
