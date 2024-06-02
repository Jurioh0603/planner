package com.triplan.planner.mypage.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MyCommunityList {
    private int bNo;
    private String title;
    private String content;
    private Date writeTime;
    private String memberId;
}
