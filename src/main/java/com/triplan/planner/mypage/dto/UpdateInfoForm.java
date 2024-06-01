package com.triplan.planner.mypage.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInfoForm {
    private String nickName;
    private String password1;
    private String password2;
}
