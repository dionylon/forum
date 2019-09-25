package com.dionysun.forum.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity {
    @Id
    private Integer id;
    @Column(length = 10)
    private String gender;
    @Column(length = 100)
    private String phone;
    @Column
    private Date birthday;
    @Column(length = 30)
    private String name;
    @Column(length = 30)
    private String password;
    @Column
    private Date gmtCreate;
    @Column
    private Date gmtModified;

}
