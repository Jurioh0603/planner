package user.controller;

import java.util.Map;

//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import kr.co.common.ReturnUtil;
//import kr.co.enums.SnsType;
import lombok.extern.slf4j.Slf4j;
import user.dto.UserDto;
import user.service.KakaoService;
import user.service.UserService;

@Controller
@Slf4j
//https://201230.tistory.com/109
//http://yoonbumtae.com/?p=1841 시큐리티넣기
//https://daegwonkim.tistory.com/268
//https://tweety1121.tistory.com/entry/Spring-Boot-security-Oauth2-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%97%B0%EB%8F%99-%EA%B5%AC%EA%B8%80%EC%B9%B4%EC%B9%B4%EC%98%A4
public class KakaoController {
	
	@Autowired
	KakaoService kakaoService;
	@Autowired
    UserService userService;
	
	//private static final String SNS_TYPE = SnsType.KAKAO.getType();
	
	@GetMapping("/member/kakao_callback")
    public void kakaoCallback(@RequestParam String code, HttpServletResponse response,  HttpSession session)  throws Exception{
		
		log.info("============================ KakaoController ========================");
        log.info(" ■■■kakao■■■ 카카오로 로그인 START");
        log.info(" ■■■kakao■■■ callback CODE:: {}",code);
        // 접속토큰 get
        String kakaoToken = kakaoService.getReturnKakaoAccessToken(code);

        // 접속자 정보 get
        Map<String, Object> result = kakaoService.getKakaoUserInfo(kakaoToken);
        log.info(" ■■■kakao■■■ result : {}",result);

        String snsId = (String) result.get("id");
        String userName = (String) result.get("nickname");
        String email = (String) result.get("email");
        String userpw = snsId;

        log.info(" ■■■kakao■■■ snsId : {} SNS_TYPE : {}" ,snsId, SNS_TYPE);
        
        // 분기
        UserDto userDto = new UserDto();
        // 일치하는 snsId 없을 시 회원가입
        System.out.println(userService.selectSnsUser(snsId, SNS_TYPE));

        if (userService.selectSnsUser(snsId, SNS_TYPE) == null) {
            
        	log.info(" ■■■kakao■■■ 카카오로 회원가입 START");

            userDto.setMember_id(email);
            userDto.setPassword(userpw);
            userDto.setName(userName);
            userDto.setSnsId(snsId);
            userDto.setEmail(email);
            userDto.setSnsType(SNS_TYPE);

            log.info(" ■■■kakao■■■ insert 전 memberVo의 값 == > "+userDto.toString());

            userService.insertMember(userDto);
        }

        // 일치하는 snsId가 있으면 맴버 객체를 세션에 저장
        UserDto memberInfo = userService.selectSnsUser(snsId, SNS_TYPE);
        
        // 회원정보 세션담기
		session.setAttribute("loginMemberInfo", memberInfo);
		// 로그아웃 처리 시, 사용할 토큰 값
		session.setAttribute("kakaoToken", kakaoToken);
		log.info(" ■■■kakao■■■ kakaoToken : "+kakaoToken);
		ReturnUtil.setReturnMessage(response, "로그인을 성공하였습니다.", "카카오회원 입니다.", "success", "/");
		
    }

}
