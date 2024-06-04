package com.triplan.planner.user.controller;

import com.triplan.planner.user.common.ReturnUtil;
import com.triplan.planner.user.common.SnsType;
import com.triplan.planner.user.dto.UserDto;
import com.triplan.planner.user.service.KakaoService;
import com.triplan.planner.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@Slf4j
public class KakaoController {

    @Autowired
    KakaoService kakaoService;
    @Autowired
    UserService userService;

    private static final String SNS_TYPE = SnsType.KAKAO.getType();

    @GetMapping("/member/kakao_callback")
    public void kakaoCallback(@RequestParam String code, HttpServletResponse response, HttpSession session) throws Exception {

        log.info("============================ KakaoController ========================");
        log.info(" ■■■kakao■■■ 카카오로 로그인 START");
        log.info(" ■■■kakao■■■ callback CODE:: {}", code);
        // 접속토큰 get
        String kakaoToken = kakaoService.getReturnKakaoAccessToken(code);

        // 접속자 정보 get
        Map<String, Object> result = kakaoService.getKakaoUserInfo(kakaoToken);
        log.info(" ■■■kakao■■■ result : {}", result);

        String snsId = (String) result.get("id");
        String userName = (String) result.get("name");
        String email = (String) result.get("email");
        String tel = (String) result.get("tel");
        String gender = (String) result.get("gender");
        String nickname = (String) result.get("nickname");
        String userpw = snsId;

        log.info(" ■■■kakao■■■ snsId : {} SNS_TYPE : {}", snsId, SNS_TYPE);

        // 분기
        UserDto userDto = new UserDto();
        // 회원정보조회
        UserDto memberInfo = userService.selectSnsUser(snsId, SNS_TYPE);

        if (memberInfo != null) {

            if ("2222".equals(memberInfo.getGrade()) || "3333".equals(memberInfo.getGrade()) ){
                ReturnUtil.setReturnMessage(response, "로그인을 할 수 없습니다. ", "권한이 없습니다.", "error", "/user/login");
            }else {
                // 회원정보 조회
                UserDto loginInfo = userService.selectSnsUser(snsId, SNS_TYPE);
                // 회원정보 세션담기
                session.setAttribute("loginMemberInfo", loginInfo);
                // 로그아웃 처리 시, 사용할 토큰 값
                session.setAttribute("kakaoToken", kakaoToken);
                log.info(" ■■■kakao■■■ kakaoToken : " + kakaoToken);
                ReturnUtil.setReturnMessage(response, "로그인을 성공하였습니다.", "카카오회원 입니다.", "success", "/index");
            }
        }else {
            log.info(" ■■■kakao■■■ 카카오로 회원가입 START");
            userDto.setMemberId(email);
            userDto.setPassword(userpw);
            userDto.setName(userName);
            userDto.setNickname(nickname);
            userDto.setGender(gender);
            userDto.setTel(tel);
            userDto.setSnsId(snsId);
            userDto.setEmail(email);
            userDto.setSnsType(SNS_TYPE);

            log.info(" ■■■kakao■■■ insert 전 memberVo의 값 == > " + userDto.toString());
            userService.insertMember(userDto);
            // 회원정보 조회
            UserDto loginInfo = userService.selectSnsUser(snsId, SNS_TYPE);
            // 회원정보 세션담기
            session.setAttribute("loginMemberInfo", loginInfo);
            // 로그아웃 처리 시, 사용할 토큰 값
            session.setAttribute("kakaoToken", kakaoToken);
            log.info(" ■■■kakao■■■ kakaoToken : " + kakaoToken);
            ReturnUtil.setReturnMessage(response, "로그인을 성공하였습니다.", "카카오회원 입니다.", "success", "/index");
        }

    }
}
