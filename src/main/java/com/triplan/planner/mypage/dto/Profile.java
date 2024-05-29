package com.triplan.planner.mypage.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private String memberId;
    private String nickName;
    private String mCopyImg;
}
