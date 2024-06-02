package com.triplan.planner.admin.dto;

import lombok.Data;

@Data
public class MemberDTO {
    //회원정보중 실제 사용하는 정보만 전송해주는 dto, 데이터 전달에 필요한 최소한의 정보만 포함
    private String memId;
    private String name;
    private String nickName;
    private String email;
    private String gender;
    private String tel;
    private int grade;

}
