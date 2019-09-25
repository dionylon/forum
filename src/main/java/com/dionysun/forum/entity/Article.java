package com.dionysun.forum.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "article")
@Entity
public class Article extends AbstractEntity {
    @Id
    private Integer id;
    private Integer authorId;
    private Integer visible;
    private String content;
    private Integer thumbUp;
    private Date gmtCreate;
    private Date gmtModified;
}
