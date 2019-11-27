package com.dionysun.forum.dao;

import com.dionysun.forum.entity.ThumbUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbUpDao extends JpaRepository<ThumbUp, Long>, CrudRepository<ThumbUp, Long> {

    /**
     * 删除点赞记录
     * @param userId user id
     * @param thumbId id
     * @param type 类型
     */
    void deleteByUserIdAndThumbIdAndType(long userId, long thumbId, int type);
    /**
     * 获取点赞数
     * @param thumbId 点赞对象的id
     * @param type 类型，0表示评论，1表示文章
     * @return 总的点赞数
     */
    int countThumbUpsByThumbIdAndType(long thumbId, int type);

    /**
     * 查询是否已经点赞过
     * @param userId 点赞人的id
     * @param thumbId 点赞对象的id
     * @param type 类型同上
     * @return 点赞记录的个数
     */
    int countThumbUpsByUserIdAndThumbIdAndType(long userId, long thumbId, int type);
}
