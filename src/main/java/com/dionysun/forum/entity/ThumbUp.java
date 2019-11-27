package com.dionysun.forum.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "thumbup")
public class ThumbUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "target_type")
    private Integer type;
    @Column(name = "thumb_id")
    private Long thumbId;
    @Column(name = "like_time")
    private Date likeTime;
    public ThumbUp(Long userId, Integer type, Long thumbId) {
        this.userId = userId;
        this.type = type;
        this.thumbId = thumbId;
        this.setLikeTime(new Date());
    }
}
