package com.triplan.planner.mypage.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyTlogList {
    private long tlogNo;
    private String tlogTitle;
    private String tlogContent;
    private String memberId;
    private String nickName;
    private String placeAreacode;
    private String storeName; // 여행기 사진
    private String mCopyImg; //프로필 사진
    private Date writeTime;
    private List<MyTlogList> myTlogLists;
}
