package com.triplan.planner.tour.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class ApiExplorer {
    @GetMapping("/tour")
    public List<Map<String, Object>> getData(String contentId) throws URISyntaxException, JsonProcessingException {
        try {
            // 고정 파라미터 값 선언
            String link = "http://apis.data.go.kr/B551011/KorService1/detailCommon1";
            String MobileOS = "ETC";
            String MobileApp = "AppTest";
            String _type = "json";
            String serviceKey = "";
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
                    "&MobileApp=" + MobileOS +
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

            System.out.println("response" + response);

            // 필요한 데이터 꺼내기
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(response, Map.class);
            System.out.println("map" + map);

            Map<String, Object> responseMap = (Map<String, Object>) map.get("response");
            System.out.println("responseMap" + responseMap);

            Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
            System.out.println("bodyMap" + bodyMap);

            // items 객체 선언
            Object items = bodyMap.get("items");
            System.out.println("items" + items);
            // items가 map으로 변환될 수 있다면 수행
            if (items instanceof Map) {
                Map<String, Object> itemsMap = (Map<String, Object>) items;
                List<Map<String, Object>> itemList = (List<Map<String, Object>>) itemsMap.get("item");
                System.out.println("Item List: " + itemList);

                return itemList;
            } else { // map으로 변환되지 않을 때 아래 구문 콘솔 출력
                System.out.println("No items found in the response.");
                return Collections.emptyList();
            }
        } catch (URISyntaxException | JsonProcessingException e){
            e.printStackTrace();

            return Collections.emptyList();
        }
    }
}
