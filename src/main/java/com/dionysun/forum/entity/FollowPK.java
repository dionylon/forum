package com.dionysun.forum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
public class FollowPK implements Serializable {
    private Long userId;
    private Long followerId;
}
