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
    private String mImg;
    private String mCopyImg;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String tel;
    private String snsType;
    private String snsId;
    private int grade;
}
