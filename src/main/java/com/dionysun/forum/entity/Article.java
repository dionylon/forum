package com.dionysun.forum.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "article")
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private Integer thumbUp;
    private Date createTime;
    private Date lastModified;
}
