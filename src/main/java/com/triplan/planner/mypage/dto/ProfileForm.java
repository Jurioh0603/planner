package com.triplan.planner.mypage.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileForm {
    private String memberId;
    private MultipartFile uploadFile;
}