package com.triplan.planner.mypage.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

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

    public MyCommunityList(int total, int page, int size, List<MyCommunityList> myCommunityList) {
    }
}
