package com.triplan.planner.chat.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    private MessageType type;
    private String roomId; // 방 번호
    private String content;
    private String sender;
    private String time; // 채팅 발송 시간
}