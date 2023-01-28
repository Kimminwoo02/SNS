package com.portfolio.sns.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmArgs {
    // 알람을 발생시킨사람
    private Integer fromUserId;
    private Integer targetId;

}

// comment : 00씨가 새 코멘트를 작성했습니다. -> postid, commentid
// 00 외 2명이 새 코멘트를 작성했습니다. -> commentid, commentid
