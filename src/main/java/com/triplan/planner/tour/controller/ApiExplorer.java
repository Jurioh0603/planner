package com.triplan.planner.tour.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triplan.planner.tour.dto.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Controller
public class ApiExplorer {
    @GetMapping("/tour")
    public Api getData(String contentId) throws URISyntaxException, JsonProcessingException {
        try {
            // 고정 파라미터 값 선언
            String link = "http://apis.data.go.kr/B551011/KorService1/detailCommon1";
            String MobileOS = "ETC";
            String MobileApp = "AppTest";
            String _type = "json";
            String serviceKey = "rqzdPo39ovzT9%2FVPMUzriYrxFac1R23NNqVSXaL4Ud9kGLfDa0Pwb8vUfo4440xNw%2BnorJKMXKccFeUbbxyi%2Fw%3D%3D";
            String overviewYN = "Y";
            String addrinfoYN = "Y";
            String firstImageYN = "Y";
            String areacodeYN = "Y";
            String defaultYN = "Y";
            Integer contentTypeId = 12; //관광 : 12

            // 파라미터 달린 url 생성
            String url = link + "?" +
                    "&serviceKey=" + serviceKey +
                    "&MobileOS=" + MobileOS +
                    "&MobileApp=" + MobileApp +
                    "&_type=" + _type +
                    "&contentId=" + contentId +
                    "&overviewYN=" + overviewYN +
                    "&addrinfoYN=" + addrinfoYN +
                    "&firstImageYN=" + firstImageYN +
                    "&defaultYN=" + defaultYN +
                    "&contentTypeId=" + contentTypeId +
                    "&areacodeYN=" + areacodeYN;

            // 지정된 URI에 HTTP GET 요청을 보내고, 해당 요청에 대한 응답을 지정된 클래스 형식으로 반환
            URI uri = new URI(url);
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(uri, String.class);

            // 필요한 데이터 꺼내기
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(response, Map.class);
            Map<String, Object> responseMap = (Map<String, Object>) map.get("response");
            Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
            // items 객체 선언
            Object items = bodyMap.get("items");
            // items가 map으로 변환될 수 있다면 수행
            if (items instanceof Map) {
                Map<String, Object> itemsMap = (Map<String, Object>) items;
                List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemsMap.get("item");
                System.out.println("Item List: " + itemList);

                Map<String, Object> item = itemList.get(0);
                return new Api(
                        (String) item.get("title"),
                        (String) item.get("addr1"),
                        (String) item.get("areacode"),
                        (String) item.get("tel"),
                        (String) item.get("homepage"),
                        (String) item.get("overview"),
                        (String) item.get("firstimage")
                );
            } else { // map으로 변환되지 않을 때 아래 구문 콘솔 출력
                System.out.println("No items found in the response.");
                return null;
            }
        } catch (URISyntaxException | JsonProcessingException e){
            e.printStackTrace();

            return null;
        }
    }
}
