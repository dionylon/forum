package com.dionysun.forum.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends AbstractEntity {
    private Integer id;
    private String gender;
    private String name;
    private String password;
}
