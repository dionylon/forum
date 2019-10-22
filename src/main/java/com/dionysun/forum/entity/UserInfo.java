package com.dionysun.forum.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    private Long id;
    private Integer fans;
    private Integer following;
    private String name;
    private Integer gender;
}
