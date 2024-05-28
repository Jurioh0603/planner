package com.triplan.planner.chat.controller;

import com.triplan.planner.chat.dto.ChatMessage;
import com.triplan.planner.chat.dto.MessageType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChatController {

    private Map<String, List<String>> rooms = new HashMap<>(); //각 채팅방의 ID에 대해 해당 방에 있는 메시지들의 리스트 저장

    // 이 메서드는 웹소켓을 통해 특정 방의 모든 구독자들에게 메시지를 전송하는 기능을 구현
    @MessageMapping("/chat.sendMessage/{roomId}") // 클라이언트가 /chat.sendMessage/{roomId}주소로 메시지 보낼때 해당 메서드가 호출되도록 지정
    @SendTo("/topic/{roomId}") // chatMessage를 /topic/{roomId} 주소로 구독하고 있는 클라이언트들에게 전송하라는것을 지시, 메서드 실행시 @DestinationVariable를 통해 얻은 실제 방 ID로 대체됨
    public ChatMessage sendMessage( // @paylaod는 메시지의 본문을 해당 객체로 매핑하라는 것을 의미
            @Payload ChatMessage chatMessage, // 클라이언트로부터 받은 메시지 데이터가 ChatMessage 타입 객체로 변환되어 chatMessage 파라미터로 전달
            @DestinationVariable String roomId) { //URL경로에서 {roomId}와 같이 동적으로 지정된 부분을 변수로 추출하여 roomId 파라미터로 전달
        return chatMessage; // @SendTo에 의해 지정된 주소로 구독자들에게 전송됨
    }


    // 이 메서드는 사용자가 특정 채팅 방에 참여할 때 해당 사용자를 방에 추가하고 이전 방에서 제거하며, 참여 메시지를 전송하는 기능을 구현
    @MessageMapping("/chat.addUser/{roomId}") //클라이언트가 /chat.addUser/{roomId}주소로 메시지 보낼때 해당 메서드가 호출됨
    @SendTo("/topic/{roomId}") // chatMessage를 /topic/{roomId} 주소로 구독하고 있는 클라이언트들에게 전송하라는것을 지시, 메서드 실행시 @DestinationVariable를 통해 얻은 실제 방 ID로 대체됨
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage, // 클라이언트로부터 받은 메시지 데이터가 ChatMessage 타입 객체로 변환되어 chatMessage 파라미터로 전달
            @DestinationVariable String roomId, //URL경로에서 {roomId}와 같이 동적으로 지정된 부분을 변수로 추출하여 roomId 파라미터로 전달
            SimpMessageHeaderAccessor headerAccessor) { // 헤더에 접근할 수 있는 SimpMessageHeaderAccessor 객체를 통해 세션속성에 접근
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("roomId", roomId); // 세션 속성에 현재 방 ID를 저장, 이전 방 ID를 currentRoomId 변수에 저장, 만약 사용자가 이미 다른 방에 참여하고 있다면 그 방의 ID가 currentRoomId에 저장
        if (currentRoomId != null) { // currentRoonId가 null이 아닌 경우, 즉 사용자가 이전에 다른방에 참여한 적이 있는 경우 확인
            rooms.get(currentRoomId).remove(chatMessage.getSender()); // 사용자가 이전에 참여했던 방에서 해당 사용자 제거, chatMessage.getSender()는 메시지 발신자를 나타냄
        }
        rooms.computeIfAbsent(roomId, k -> new ArrayList<>()).add(chatMessage.getSender()); // 사용자가 참여하고자 하는 방이 rooms맵에 없으면 새로운 리스트 생성하여 추가, 해당 방에 사용자 추가
        chatMessage.setType(MessageType.JOIN); // 메시지 타입을 JOIN으로 설정, 이는 사용자가 방에 참여했음을 나타내는 메시지 타입
        return chatMessage; // chatMessage 객체 반환
    }


    @RequestMapping("/chat/openChat")
    public String test(){
        return "/chat/openChat";
    }

}
