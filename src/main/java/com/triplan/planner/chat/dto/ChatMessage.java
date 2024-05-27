package com.triplan.planner.chat.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    private String roomId;
    private String content;
    private String sender;
    private MessageType type;
}
