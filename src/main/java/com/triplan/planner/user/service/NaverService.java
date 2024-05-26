package com.triplan.planner.user.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.util.Map;

@Slf4j
@Service
public class NaverService {

	@Value("${naver.api.client.id}")
	private String NAVER_CLIENT_ID;
	
	@Value("${naver.api.callback.uri}")
	private String NAVER_API_CALLBACK_URI;

	@Value("${naver.api.secret.key}")
	private String NAVER_SECRET_KEY;
	
	//네이버 로그인 연동 요청 기본 URI
	private static final String NAVER_REQUEST_URI = "https://nid.naver.com/oauth2.0/";
	
	//네이버 유저정보 요청 URI
	private static final String NAVER_USER_INFO_REQUEST_URI = "https://openapi.naver.com/v1/nid/me";
	
	//억세스 토큰을 요청하기위한 코드 요청
	public String getNaverAuthorizeUrl(HttpSession session, String type) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {

		SecureRandom random = new SecureRandom();
		String setState = new BigInteger(130, random).toString();
		session.setAttribute("state", setState);
		
        UriComponents uriComponents = UriComponentsBuilder
    		.fromUriString(NAVER_REQUEST_URI + type)
            .queryParam("response_type", "code")
            .queryParam("client_id", NAVER_CLIENT_ID)
            .queryParam("redirect_uri", URLEncoder.encode(NAVER_API_CALLBACK_URI, "UTF-8"))
            .queryParam("state", URLEncoder.encode(setState, "UTF-8"))
            .build();

        return uriComponents.toString();
		
	}

	//네이버 억세스토큰정보 요청
	public String getReturnNaverAccessToken(String type, String code, String state) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {

        try {

        	// .com?key=value&key2=value2&key3=value3 형태로 보냄
        	UriComponents uriComponents = UriComponentsBuilder
    			.fromUriString(NAVER_REQUEST_URI + type)
    			.queryParam("grant_type", "authorization_code")
    			.queryParam("client_id", NAVER_CLIENT_ID)
    			.queryParam("client_secret", NAVER_SECRET_KEY)
    			.queryParam("code", code)
    			.queryParam("state", URLEncoder.encode(state, "UTF-8"))
    			.build();
	        
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest naverApiRequest = HttpRequest.newBuilder()
                .uri(URI.create(uriComponents.toString()))
                .GET()
                .build();

	        String logMessage = 
	        		"\n ■■■naver■■■ Method	: " + naverApiRequest.method() +
	                "\n ■■■naver■■■ URI		: " + naverApiRequest.uri() +
	                "\n ■■■naver■■■ Headers	: " + naverApiRequest.headers() +
	                "\n ■■■naver■■■ Body		: " + uriComponents.toString();

	        log.info(" ■■■naver■■■ naverApiRequest 전송정보 : {}",logMessage);
            
            HttpResponse<String> naverApiResponse = httpClient.send(naverApiRequest, HttpResponse.BodyHandlers.ofString());

            int statusCode = naverApiResponse.statusCode();

            String responseBody = naverApiResponse.body();

	        log.info(" ■■■naver■■■ naverApiResponse : {}",naverApiResponse.toString());
	        log.info(" ■■■naver■■■ naverApiResponse statusCode : {}",naverApiResponse.statusCode());
            
            if (statusCode == 200) { // Success
                return responseBody;
            } else { // Error
            	log.info(" ■■■naver■■■ Error naverApiResponse: " + responseBody);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	//네이버 회원정보 요청
	public Map<String, Object> getNaverUserInfo(String accessToken, String tokenType) {
		
		TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>() {};
		ObjectMapper objectMapper = new ObjectMapper();
		
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest naverApiRequest = HttpRequest.newBuilder()
                    .uri(URI.create(NAVER_USER_INFO_REQUEST_URI))
                    .header("Authorization", tokenType + " " + accessToken)
                    .GET()
                    .build();

	        String logMessage = 
	        		"\n ■■■naver■■■ Method	: " + naverApiRequest.method() +
	                "\n ■■■naver■■■ URI		: " + naverApiRequest.uri() +
	                "\n ■■■naver■■■ Headers	: " + naverApiRequest.headers();

	        log.info(" ■■■naver■■■ naverApiRequest 전송정보 : {}",logMessage);
            
            HttpResponse<String> naverApiResponse = httpClient.send(naverApiRequest, HttpResponse.BodyHandlers.ofString());

            int statusCode = naverApiResponse.statusCode();

            String responseBody = naverApiResponse.body();

	        log.info(" ■■■naver■■■ naverApiResponse : {}",naverApiResponse.toString());
	        log.info(" ■■■naver■■■ naverApiResponse statusCode : {}",naverApiResponse.statusCode());
            
            Map<String, Object> resultUserMap = objectMapper.readValue(responseBody, typeReference);
            
            if (statusCode == 200) { // Success
                return resultUserMap;
            } else { // Error
            	log.info(" ■■■naver■■■  Error response: " + responseBody);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
			
    }
}