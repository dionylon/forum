package com.dionysun.forum.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "follow")
@IdClass(FollowPK.class)
@EqualsAndHashCode()
public class Follow {
    @Id
    private Long userId;
    @Id
    private Long followerId;
}
