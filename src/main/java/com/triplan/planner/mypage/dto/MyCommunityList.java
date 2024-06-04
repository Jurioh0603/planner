package com.triplan.planner.mypage.dto;

import lombok.*;

import java.text.SimpleDateFormat;
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
    private String nickName;
    private String communityType;
    private String mCopyImg;

    public String getDateFormat(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        return simpleDateFormat.format(writeTime);
    }
}
