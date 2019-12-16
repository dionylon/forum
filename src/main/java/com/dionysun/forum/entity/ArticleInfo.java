package com.dionysun.forum.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "article_info")
@Entity
public class ArticleInfo {
    @Id
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private Date createTime;
    private Date lastModified;

    private Integer thumbUp;
}
