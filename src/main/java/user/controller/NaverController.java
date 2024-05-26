package user.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import kr.co.common.ReturnUtil;
//import kr.co.enums.SnsType;

import com.fasterxml.jackson.core.type.TypeReference;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import user.dto.UserDto;
import user.service.NaverService;
import user.service.UserService;

@Slf4j
@AllArgsConstructor
@Controller
//https://cobook.tistory.com/31
//https://velog.io/@jaeygun/Spring-boot-Naver-Login-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0feat.-Thymeleaf
//http://yoonbumtae.com/?p=1818
public class NaverController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	NaverService naverService;

	private static final String SNS_TYPE = SnsType.NAVER.getType();
	
	@GetMapping("/member/naver_login")
    public void naverLogin(HttpServletResponse response,  HttpSession session)  throws Exception{

		log.info("============================ NaverController ========================");
        log.info(" ■■■naver■■■ 네이버 로그인 START");
		String naverReuqstUrl = naverService.getNaverAuthorizeUrl(session, "authorize");
        try {
        	response.sendRedirect(naverReuqstUrl);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	@GetMapping("/member/naver_callback")
    public void naverCallback(
		@RequestParam String code
		, @RequestParam String state
		, @RequestParam(required = false) String error
		, @RequestParam(required = false) String error_description
		, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws MalformedURLException, UnsupportedEncodingException, URISyntaxException, JsonProcessingException {
        
		if (!"".equals(error) && error != "" && error != null) {
        	log.info(" ■■■naver■■■ 네이버 로그인 에러 error_description : {}",error_description);
        }

		ObjectMapper objectMapper = new ObjectMapper();
	    
		TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>() {};
	    
        String responseToken = naverService.getReturnNaverAccessToken("token", code, state);
        
        //json to Map
		Map<String, Object> resulTokentMap = objectMapper.readValue(responseToken, typeReference);
		
		log.info(" ■■■naver■■■ accessToken : {}",	(String) resulTokentMap.get("access_token"));	
		log.info(" ■■■naver■■■ refreshToken : {}",	(String) resulTokentMap.get("refresh_token"));	
		log.info(" ■■■naver■■■ tokenType : {}",		(String) resulTokentMap.get("token_type"));		

		String accessToken 	= (String) resulTokentMap.get("access_token");
		String refreshToken = (String) resulTokentMap.get("refresh_token");
		String tokenType 	= (String) resulTokentMap.get("token_type");
		String expiresIn 	= (String) resulTokentMap.get("expires_in");
		
		Map<String, Object> resultUserMap = naverService.getNaverUserInfo(accessToken, tokenType);
        
        log.info(" ■■■naver■■■ resultUserMap 아이디 	: {}", ((HashMap<?, ?>) resultUserMap.get("response")).get("id"));	
		log.info(" ■■■naver■■■ resultUserMap 닉네임 	: {}", ((HashMap<?, ?>) resultUserMap.get("response")).get("nickname"));	
		log.info(" ■■■naver■■■ resultUserMap 이름	 	: {}", ((HashMap<?, ?>) resultUserMap.get("response")).get("name"));	
		log.info(" ■■■naver■■■ resultUserMap 연락처 	: {}", ((HashMap<?, ?>) resultUserMap.get("response")).get("mobile"));	
		log.info(" ■■■naver■■■ resultUserMap 성별		: {}", ((HashMap<?, ?>) resultUserMap.get("response")).get("gender"));	
		log.info(" ■■■naver■■■ resultUserMap 프로필사진	: {}", ((HashMap<?, ?>) resultUserMap.get("response")).get("profile_image"));	
		
        String snsId = (String) ((HashMap<?, ?>) resultUserMap.get("response")).get("id");
        String userName = (String) ((HashMap<?, ?>) resultUserMap.get("response")).get("name");
        String email = (String) ((HashMap<?, ?>) resultUserMap.get("response")).get("email");
        String mobile = (String) ((HashMap<?, ?>) resultUserMap.get("response")).get("mobile");
        String userpw = snsId;
		
	    // 분기
		UserDto userDto = new UserDto();
        
        // 일치하는 snsId 없을 시 회원가입 처리
        System.out.println(userService.selectSnsUser(snsId, SNS_TYPE));

        if (userService.selectSnsUser(snsId, SNS_TYPE) == null) {
            
        	log.info(" ■■■naver■■■ 네이버로 회원가입 START");

        	userDto.member_id(email);
			userDto.setPassword(userpw);
			userDto.setName(userName);
			userDto.setSnsId(snsId);
			userDto.setEmail(email);
			userDto.setTel(mobile);
			userDto.setSnsType(SNS_TYPE);

            log.info(" ■■■naver■■■ insert 전 memberVo의 값 == > "+userDto.toString());
            
            userService.insertMember(userDto);
        }
        // 일치하는 snsId가 있으면 맴버 객체를 세션에 저장
        userDto memberInfo = userService.selectSnsUser(snsId, SNS_TYPE);
        // 회원정보 세션담기
		session.setAttribute("loginMemberInfo", memberInfo);
		// 로그아웃 처리 시, 사용할 토큰 값
		session.setAttribute("naverToken", accessToken);
		log.info(" ■■■naver■■■ naverToken : "+accessToken);
		
		//ReturnUtil.setReturnMessage(response, "로그인을 성공하였습니다.", "네이버회원 입니다.", "success", "/");
        
    }
	
	
}
