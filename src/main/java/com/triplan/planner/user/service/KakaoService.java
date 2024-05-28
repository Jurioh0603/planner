package com.triplan.planner.user.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class KakaoService {
	
	@Value("${kakao.api.callback.uri}")
	private String KAKAO_API_CALLBACK_URI;
	
	@Value("${kakao.api.rest.key}")
	private String KAKAO_API_RESTKEY;
	
	private static final String KAKAO_TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";
	
	private static final String KAKAO_USER_INFO_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";

	public String getReturnKakaoAccessToken(String code) {
		
	    String access_token = "";

	    try {
	    	
	    	//전송
	        String parameters = String.format("grant_type=authorization_code&client_id=%s&code=%s", KAKAO_API_RESTKEY, code);
	        
	        HttpClient httpClient = HttpClient.newHttpClient();
	        
	        HttpRequest kakaoApiRequest = HttpRequest.newBuilder()
                .uri(URI.create(KAKAO_TOKEN_REQUEST_URL))
                .POST(HttpRequest.BodyPublishers.ofString(parameters))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
	        
	        String logMessage = 
        		"\n■■■kakao■■■ Method: " + kakaoApiRequest.method() +
                "\n■■■kakao■■■ URI: " + kakaoApiRequest.uri() +
                "\n■■■kakao■■■ Headers: " + kakaoApiRequest.headers() +
                "\n■■■kakao■■■ Body: " + parameters;

	        log.info("■■■kakao■■■ kakaoApiRequest 전송정보 : {}",logMessage);
	        
	        HttpResponse<String> kakaoApiResponse = httpClient.send(kakaoApiRequest, HttpResponse.BodyHandlers.ofString());
	        
	        log.info("■■■kakao■■■ kakaoApiResponse : {}",kakaoApiResponse.toString());
	        log.info("■■■kakao■■■ kakaoApiResponse statusCode : {}",kakaoApiResponse.statusCode());
	        
	        if (kakaoApiResponse.statusCode() == 200) {

	        	String result = kakaoApiResponse.body();
	            // 토큰 값 저장 및 리턴
	            JsonElement element = JsonParser.parseString(result);
	            access_token = element.getAsJsonObject().get("access_token").getAsString();
	            
		        log.info("■■■kakao■■■ access_token : {}",access_token);

		        return access_token;

	        } else {
	            // Handle non-200 response code.
    	        log.info("■■■kakao■■■ kakaoApiResponse statusCode : {}",kakaoApiResponse.statusCode());
            	return null;
	        }
	        

	    } catch (IOException | InterruptedException e) {
	    	e.printStackTrace();
	    }

	    return null;
	
	}

	public Map<String, Object> getKakaoUserInfo(String access_token) {
		
	    Map<String, Object> resultMap = new HashMap<>();

	    try {
	    	
        	UriComponents uriComponents = UriComponentsBuilder
    			.fromUriString(KAKAO_USER_INFO_REQUEST_URL)
    			//.queryParam("Authorization", "Bearer " + access_token)
    			.build();

            HttpClient httpClient = HttpClient.newHttpClient();      
            
            
            HttpRequest kakaoApiRequest = HttpRequest.newBuilder()
        		.uri(URI.create(uriComponents.toString()))
                .header("Authorization", "Bearer " + access_token)	//HttpURLConnection setRequestProperty는 HttpRequest에서 header로 사용한다.
                .GET()
                .build();
            
	        String logMessage = 
        		"\n■■■kakao■■■ Method: " + kakaoApiRequest.method() +
                "\n■■■kakao■■■ URI: " + kakaoApiRequest.uri() +
                "\n■■■kakao■■■ Headers: " + kakaoApiRequest.headers();

	        log.info("■■■kakao■■■ kakaoApiRequest 전송정보 : {}",logMessage);
	        
            HttpResponse<String> kakaoApiResponse = httpClient.send(kakaoApiRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

	        log.info("■■■kakao■■■ kakaoApiResponse : {}",kakaoApiResponse.toString());
	        log.info("■■■kakao■■■ kakaoApiResponse statusCode : {}",kakaoApiResponse.statusCode());
            
            if (kakaoApiResponse.statusCode() == 200) {

            	String result = kakaoApiResponse.body();

    	        JsonElement element = JsonParser.parseString(result);
    	        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
    	        JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

				String id = element.getAsJsonObject().get("id").getAsString();
				String nickname = properties.getAsJsonObject().get("nickname").getAsString();
				String email = kakao_account.getAsJsonObject().get("email").getAsString();
				String name = kakao_account.getAsJsonObject().get("profile").getAsJsonObject().get("nickname").getAsString();
				String gender = kakao_account.getAsJsonObject().get("gender").getAsString();
				if ("female".equalsIgnoreCase(gender)) {
					gender = "W";
				} else if ("male".equalsIgnoreCase(gender)) {
					gender = "M";
				} else {
					gender = "U";
				}
				String tel = kakao_account.getAsJsonObject().get("phone_number").getAsString();
				String profileImageUrl = properties.getAsJsonObject().get("profile_image").getAsString();

    	        log.info(" ■■■kakao■■■ id : {}", id);
				log.info(" ■■■kakao■■■ name : {}", name);
    	        log.info(" ■■■kakao■■■ email : {}",	email);
				log.info(" ■■■kakao■■■ tel : {}", tel);
				log.info(" ■■■kakao■■■ gender : {}", gender);
    	        log.info(" ■■■kakao■■■ nickname : {}", nickname);
    	        log.info(" ■■■kakao■■■ profileImageUrl : {}", profileImageUrl);

                resultMap.put("id", id);
                resultMap.put("email", email);
                resultMap.put("nickname", nickname);
				resultMap.put("name",name);
				resultMap.put("tel",tel);
				resultMap.put("gender",gender);
				resultMap.put("profileImageUrl",profileImageUrl);

                return resultMap;

            } else {
                // Handle non-200 response code.
    	        log.info("■■■kakao■■■ kakaoApiResponse statusCode : {}",kakaoApiResponse.statusCode());
            	return null;
            }

        } catch (IOException | InterruptedException e) {
	    	e.printStackTrace();
	    }

        return null;
	    
	}
}
