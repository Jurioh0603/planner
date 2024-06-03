package com.triplan.planner.admin.domain;

import lombok.Data;

//domain: 비즈니스 로직을 포함하는 객체
@Data
public class Member {
    private String memId;
    private String name;
    private String password;
    private String nickName;
    private String email;
    private String gender;
    private String tel;
    private int grade;
}
