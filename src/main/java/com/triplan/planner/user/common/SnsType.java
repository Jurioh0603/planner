package com.triplan.planner.user.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SnsType implements Code {

    KAKAO("K", "카카오"),
    NAVER("N", "네이버"),
    ;
    private final String type;
    private final String message;
}
