package com.dionysun.forum.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "comment")
public class Comment extends AbstractEntity {
    @Id
    private Integer id;
    private Integer articleId;
    private Integer authorId;
    private String content;
    private Date gmtCreate;
    private Date gmtModified;
}
