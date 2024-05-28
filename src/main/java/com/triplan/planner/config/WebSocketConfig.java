package com.triplan.planner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 이 메서드는 STOMP엔트포인트를 등록하기 위한 메서드, 이 메서드는 애플리케이션이 WebSocket을 사용하여 클라이언트와 통신할 수 있는 엔트포인트 설정시 호출
    @Override // addEndpoint("/ws")는 클라이언트가 WebSocket 연결을 시작할 수 있는 경로를 설정
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    // 이 메서드는 WebSocket을 통한 실시간 웹 애플리케이션을 구현하기 위한 기본 설정을 제공, 양방향 통신을 설정하고, 메시지를 보내고 받을 수 있는 경로를 정의
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); // 애플리케이션이 처리할 메시지의 목적지(prefix)를 설정 클라이언트가 "/app"으로 시작하는 모든 경로로 메시지를 보내면, @Controller 클래스의 @MessageMapping 어노테이션이 달린 메서드에서 해당 메시지를 처리
        registry.enableSimpleBroker("/topic"); // 단순한 메모리 기반의 메시지 브로커를 활성화, 클라이언트가 "/topic"으로 시작하는 경로를 구독할 수 있게 함
    }
}
