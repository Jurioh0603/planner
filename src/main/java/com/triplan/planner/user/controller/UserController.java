package com.triplan.planner.user.controller;

import com.triplan.planner.user.common.ReturnUtil;
import com.triplan.planner.user.dto.UserDto;
import com.triplan.planner.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	//카카오
    @Value("${kakao.api.callback.uri}")	//카카오 콜백URL
    private String kakaoCallbackUri;
    @Value("${kakao.api.javascript.key}")	//카카오 자바스크립트 호출 키
    private String kakaoJavascriptKey;

    //네이버
    @Value("${naver.api.client.id}")
	private String NAVER_CLIENT_ID;
	@Value("${naver.api.callback.uri}")
	private String NAVER_API_CALLBACK_URI;
	@Value("${naver.api.secret.key}")
	private String NAVER_SECRET_KEY;

    
    //공통 로그인 페이지 이동
	@GetMapping("/login")
	public String login(Model model, HttpServletRequest request, HttpSession session) {
    	log.info("Controller @GetMapping( /user/login ) 로그인 화면이동 >>>>>>>>>>>>>>> ");
    	model.addAttribute("kakaoCallbackUri", kakaoCallbackUri);
    	model.addAttribute("kakaoJavascriptKey", kakaoJavascriptKey);

		if(session.getAttribute("interceptorRedirectURL") == null) {
			// 이전 페이지로 이동하기 위한 세션 값 redirectURL 저장
			String requestURI = request.getHeader("Referer");
			System.out.println(requestURI);
			session.setAttribute("redirectURL", requestURI);
		}

    	return "user/login";
	}
	
    //일반회원 로그인 처리
	@PostMapping("/login")
	public void selectLogin(UserDto userDto, HttpServletResponse response, HttpSession session) {

		UserDto loginInfo = userService.selectLogin(userDto);

        //회원정보가 있으면 로그인 처리
        if(loginInfo != null) {
			if ("2222".equals(loginInfo.getGrade()) || "3333".equals(loginInfo.getGrade()) ) {
				ReturnUtil.setReturnMessage(response, "로그인을 할 수 없습니다. ", "권한이 없습니다.", "error", "/user/login");
			}else {
				// 회원정보 세션담기
				session.setAttribute("loginMemberInfo", loginInfo);

				// 이전 페이지로 이동하기 위한 세션 값 redirectURL 가져오기
				String redirectURL = (String) session.getAttribute("interceptorRedirectURL");
				if(redirectURL != null) {
					session.removeAttribute("interceptorRedirectURL");
				} else {
					redirectURL = (String) session.getAttribute("redirectURL");
					if(redirectURL == null || redirectURL.contains("user"))
						redirectURL = "/index";
					session.removeAttribute("redirectURL");
				}

				ReturnUtil.setReturnMessage(response, "로그인을 성공하였습니다.", "", "success", redirectURL);
			}
		}else {
    		ReturnUtil.setReturnMessage(response, "로그인을 실패하였습니다.", "", "error", "/user/login");
        }
	}
	
	//공통 로그아웃 (SNS회원 / 일반회원)
	@GetMapping("/logout")
	public void logout(HttpServletResponse response, HttpSession session) {

		log.info("Controller @GetMapping( /user/logout ) 로그아웃 처리 >>>>>>>>>>>>>>> ");
		//카카오 로그인일시
		String kakaoLogoutUrl = "https://kapi.kakao.com/v1/user/logout";
		String kakaoAccessToken  = (String) session.getAttribute("kakaoToken");
		//네이버 로그인일시
		String naverLogoutUrl = "https://nid.naver.com/oauth2.0/token";
		String naverAccessToken  = (String) session.getAttribute("naverToken");
		//성공 메세지
		String subMessage = "";
		
		//카카오 로그인 회원일시 로그아웃 프로세스 시작
		if(kakaoAccessToken != null && !kakaoAccessToken.isEmpty()) {
			log.info(" ======= kakao access_token : "+kakaoAccessToken);
			
			try {

		        HttpClient httpClient = HttpClient.newHttpClient();

		        HttpRequest kakaoApiRequest = HttpRequest.newBuilder()
		                .uri(URI.create(kakaoLogoutUrl))
		                .POST(HttpRequest.BodyPublishers.noBody())
		                .header("Authorization", "Bearer " + kakaoAccessToken)
		                .build();
		        
		        HttpResponse<String> kakaoApiResponse = httpClient.send(kakaoApiRequest, HttpResponse.BodyHandlers.ofString());
		        
		        log.info("카카오 로그아웃 리턴코드 : {} / statusCode : {} ",kakaoApiResponse, kakaoApiResponse.statusCode());

		        String result = "";
		        
		        if (kakaoApiResponse.statusCode() == 200) {
		        	result = kakaoApiResponse.body();
		            // Process the result as needed.
		        } else {
		            // Handle non-200 response code.
		        }
		        
		        log.info("카카오 로그아웃 결과 : {}", result);
		    
		        subMessage = "kakao 로그인 종료";

			} catch (IOException | InterruptedException e) {
		    	e.printStackTrace();
		    }
		 }
		
		
		//네이버 로그인 회원일시 로그아웃 프로세스 시작
		if(naverAccessToken != null && !naverAccessToken.isEmpty()) {

			log.info(" ======= naver access_token : "+naverAccessToken);
			
		    try {
		    	
		    	UriComponents uriComponents = UriComponentsBuilder
		                .fromUriString(naverLogoutUrl)
		                .queryParam("grant_type", "delete")
		                .queryParam("client_id", NAVER_CLIENT_ID)
		                .queryParam("client_secret", NAVER_SECRET_KEY)
		                .queryParam("access_token", naverAccessToken)
		                .queryParam("service_provider", URLEncoder.encode("NAVER", "UTF-8"))
		                .build();
		    	
		    	
	            HttpClient httpClient = HttpClient.newHttpClient();

	            HttpRequest naverApiRequest = HttpRequest.newBuilder()
	                    .uri(URI.create(uriComponents.toString()))
	                    .GET()
	                    .build();
		    	
	            HttpResponse<String> naverApiResponse = httpClient.send(naverApiRequest, HttpResponse.BodyHandlers.ofString());

	            int statusCode = naverApiResponse.statusCode();

	            String responseBody = naverApiResponse.body();
	            
	            log.info("네이버 로그아웃 statusCode코드 : " + statusCode);
	            log.info("네이버 로그아웃 responseBody코드 : " + responseBody);
	            
		        subMessage = "Naver 로그인 종료";

	        } catch (IOException | InterruptedException e) {
		    	e.printStackTrace();
		    }

		}
     	
    	//로그인으로 생성한 세션을 다 지운다.
		session.invalidate();
		ReturnUtil.setReturnMessage(response, "로그아웃을 하였습니다.", subMessage, "success", "/index");

	}

	//회원가입 페이지 이동
	@GetMapping("/join")
	public String join() {
		log.info("Controller @GetMapping(/user/join) 회원가입 화면이동");
		return "user/join";
	}

	//회원 등록처리
	@PostMapping("/insertMember")
	public void insertMember(HttpServletResponse response, UserDto userDto) throws Exception {

    	log.info("Controller @PostMapping(/user/insertMember) 회원가입 처리");

		UserDto chkMemberDto = new UserDto();

		chkMemberDto.setMemberId(userDto.getMemberId());
		
		//중복아이디 검사
		
		try {
		
		int chkCnt = userService.insertCheck(chkMemberDto);
		
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> chkCnt " +chkCnt);
		
		if(chkCnt != 0) {
		
			ReturnUtil.setReturnMessage(response, "이미 가입된 아이디입니다.", "다른 아이디를 사용해주세요.", "warning", "/user/join");

		}else {

			int result = userService.insertMember(userDto);
			
			//마이바티스에서 insert를 성공하면 숫자 1을 반환하고 실패시 0을 반환한다.
			//1이라면 메인페이지로 이동. 0이라면 회원가입 페이지로 다시 이동
			if(result > 0) {
				ReturnUtil.setReturnMessage(response, "회원가입을 하였습니다.","로그인 후 이용해주세요.", "success", "/user/login");
			}else {
				ReturnUtil.setReturnMessage(response, "회원가입을 실패였습니다.", "", "error", "/user/join");
			}
			
		}
		
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}

	@GetMapping("/passwordFind")
	public String passwordFind() {
		log.info("Controller @GetMapping(/user/passwordFind) 비밀번호찾기 화면이동");
		return "user/passwordFind";
	}

	@PostMapping("/passwordUpdate")
	public void passwordUpdate(HttpServletResponse response, UserDto userdDto) {
		log.info("Controller @GetMapping(/user/passwordUpdate) 새비밀번호로 업데이트");

		String alertMessage = "";
		String icon = "";

		if(userService.passwordUpdate(userdDto)) {
			alertMessage = "이메일로 신규비밀번호를 발송하였습니다.";
			icon = "success";
		}else {
			alertMessage = "일치하는 메일주소가 없습니다.";
			icon = "error";
		}

		ReturnUtil.setReturnMessage(response, alertMessage, "", icon, "/user/login");
	}


	//test
//	@Autowired
//	TestMapper testMapper;
//	@GetMapping("/test")
//	public String DBConnectionTest() {
//		System.out.println("DBConnectionTest 호출");
//		testMapper.test();
//		return "redirect:/";
//	}
	//Weather test
//	@GetMapping("/weather")
//	public String weather() {
//		return "weather/dailyWeather";
//	}
}