package com.codefolio.vo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowVO {
    private int followSeq;
    private String followerId;
    private String followerStack;
    private String follwerImg;
    private String followeeId;
    private String followeeStack;
    private String follweeImg;
}
