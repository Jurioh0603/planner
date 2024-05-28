package com.triplan.planner.tour;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ApiExplorer {
    @RequestMapping("/tour")
    public List<Map<String, Object>> getData(Integer areaCode, String state, Integer contentTypeId, Integer numOfRows) throws URISyntaxException, JsonProcessingException {
        String link = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1";
        String MobileOS = "ETC";
        String MobileApp = "Test";
        String _type = "json";
        String serviceKey = "rqzdPo39ovzT9%2FVPMUzriYrxFac1R23NNqVSXaL4Ud9kGLfDa0Pwb8vUfo4440xNw%2BnorJKMXKccFeUbbxyi%2Fw%3D%3D";

        String url = link + "?" +
                "&MobileOS=" + MobileOS +
                "&MobileApp=" + MobileApp +
                "&_type=" + _type +
                "&areaCode=" + areaCode +
                "&contentTypeId=" + contentTypeId +
                "&numOfRows=" + numOfRows +
                "&serviceKey=" + serviceKey;

        URI uri = new URI(url);
        RestTemplate restTemplate = new RestTemplate();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .headers("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();

        String response = restTemplate.getForObject(
                uri,
                String.class
        );
        
        Map<String, Object> map = new ObjectMapper().readValue(response.toString(), Map.class);
        Map<String, Object> responseMap = (Map<String, Object>) map.get("response");
        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
        Map<String, Object> itemsMap = (Map<String, Object>) bodyMap.get("items");
        List<Map<String,Object>> itemMap = (List<Map<String, Object>>) itemsMap.get("item");

        //state에 있는 정보만 들고오기
        List<Map<String, Object>> testItemMap = itemMap.stream()
                .filter(item -> {
                    Object value = item.get("addr1");
                    return value != null && value.toString().contains(state);
                })
                .collect(Collectors.toList());
        return testItemMap;
    }
}
