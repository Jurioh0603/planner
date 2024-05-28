package com.triplan.planner.config;


import com.triplan.planner.chat.dto.ChatMessage;
import com.triplan.planner.chat.dto.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate; // SimpMessageSendingOperations인터페이스 구현 객체를 정의

    // 이 메서드는 WebSocket연결이 끊어졌을 때 해당 사용자 정보를 로그에 기록, 다른 클라이언트에게 연결 종료 메시지 전송하는 기능 수행
    @EventListener
    public void handleWebSocketDisconnectionListener(
            SessionDisconnectEvent event
    ) { // @EventListener 를 사용해 SessionDisconnectEvent가 발생했을 때 호출되는 메서드, WebSocket 세션이 끊어졌을 때 실행
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage()); // StompHeaderAccessor를 사용하여 event에서 메세지 헤더를 추출(메시지 세부정보 읽을 수 있음)
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) { // 사용자가 연결되어있는지 확인
            log.info("User disconnected: {}", username); // 로그에 사용자가 연결을 끊었다는 메시지 기록
            var chatMessage = ChatMessage.builder() // ChatMessage객체를 생성, 메시지 유형은 type,LEAVE, 연결을 끊은 사용자의 이름으로 설정
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            messageTemplate.convertAndSend("/topic/{roomId}", chatMessage);
        }   // messageTemplate을 사용하여 /topic/{roomId} 경로로 chatMessage를 전송
    }

}
