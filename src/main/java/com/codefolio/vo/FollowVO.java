package com.codefolio.vo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowVO {
    private int followSeq;
    private String followerUUID;
    private String followeeUUID;
    private String status;
}
