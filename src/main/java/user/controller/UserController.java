package user.controller;

//import kr.co.common.ReturnUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import user.dto.UserDto;
import user.service.UserService;

//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
  * @FileName : MemberController.java
  * @Project : SpringBoot2Jdk11MybatisOracleSocialLoginNoSecuritySample
  * @Date : 
  * @작성자 : 
  * @변경이력 :
  * @프로그램 설명 : 로그인 컨트롤러
  */
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
	public String login(Model model) {
    	log.info("Controller @GetMapping( /user/login ) 로그인 화면이동 >>>>>>>>>>>>>>> ");
    	model.addAttribute("kakaoCallbackUri", kakaoCallbackUri);
    	model.addAttribute("kakaoJavascriptKey", kakaoJavascriptKey);
    	return "user/login";
	}
	
    //일반회원 로그인 처리
	@PostMapping("/login")
	public void selectLogin(UserDto userDto, HttpServletResponse response, HttpSession session) {

		UserDto loginInfo = userService.selectLogin(userDto);
		//MemberVo loginInfo = memberService.selectLogin(memberVo);
        		
        //회원정보가 있으면 로그인 처리
        if(loginInfo != null) {
            // 회원정보 세션담기
    		session.setAttribute("loginMemberInfo", loginInfo);
    		ReturnUtil.setReturnMessage(response, "로그인을 성공하였습니다.", "", "success", "/");        	
        }else {
    		ReturnUtil.setReturnMessage(response, "로그인을 실패하였습니다.", "", "error", "/");        	
        }

    			;
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
		ReturnUtil.setReturnMessage(response, "로그아웃을 하였습니다.", subMessage, "success", "/");

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
		
		//MemberVo chkMemberVo = new MemberVo();
		UserDto chkMemberDto = new UserDto();

		chkMemberDto.setMember_id(userDto.getMember_id());
		
		//중복아이디 검사
		
		try {
		
		int chkCnt = userService.insertCheck(chkMemberDto);
		
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> chkCnt " +chkCnt);
		
		if(chkCnt != 0) {
		
			ReturnUtil.setReturnMessage(response, "이미 가입된 아이디입니다.", "다른 아이디를 사용해주세요.", "warning", "/member/join");

		}else {

			int result = userService.insertMember(userDto);
			
			//마이바티스에서 insert를 성공하면 숫자 1을 반환하고 실패시 0을 반환한다.
			//1이라면 메인페이지로 이동. 0이라면 회원가입 페이지로 다시 이동
			if(result > 0) {
				ReturnUtil.setReturnMessage(response, "회원가입을 하였습니다.","로그인 후 이용해주세요.", "success", "/");
			}else {
				ReturnUtil.setReturnMessage(response, "회원가입을 실패였습니다.", "", "error", "/tiles/member/join");
			}
			
		}
		
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}	

	@GetMapping("/updateStatus")
	public void updateStatue(HttpServletResponse response, UserDto userDto){

		//ReturnUtil returnUtil = new ReturnUtil();

		int result = userService.updateStatus(userDto);
		
		//마이바티스에서 insert를 성공하면 숫자 1을 반환하고 실패시 0을 반환한다.
		//1이라면 메인페이지로 이동. 0이라면 회원가입 페이지로 다시 이동
		if(result > 0) {
			ReturnUtil.setReturnMessage(response, "회원상태를 변경하였습니다.","", "success", "/notiles/admin/main");
		}else {
			ReturnUtil.setReturnMessage(response, "회원상태 변경을 실패였습니다.","", "error", "/notiles/admin/main");
		}		
		
	}

	
	@GetMapping("/mypage")
	public ModelAndView mypage() {
    	log.info("Controller @GetMapping(/user/mypage) 마이페이지 화면이동");
    	ModelAndView mv = new ModelAndView("member/mypage");
    	mv.addObject("memberList", userService.selecMemberList());
    	return mv;
	}
}