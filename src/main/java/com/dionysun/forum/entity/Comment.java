package com.dionysun.forum.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "comment")
@NamedStoredProcedureQuery(name = "addComments", procedureName = "addComments",
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "articleId"),
        @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "authorId"),
        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "total"),
        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "template")
    })// 参数顺序必须对应存储过程的参数的顺序!!!
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long articleId;
    private Long authorId;
    private String content;
    private Date createTime;
}
