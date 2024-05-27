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

    private Map<String, List<String>> rooms = new HashMap<>();

    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage,
            @DestinationVariable String roomId) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            @DestinationVariable String roomId,
            SimpMessageHeaderAccessor headerAccessor) {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("roomId", roomId);
        if (currentRoomId != null) {
            rooms.get(currentRoomId).remove(chatMessage.getSender());
        }
        rooms.computeIfAbsent(roomId, k -> new ArrayList<>()).add(chatMessage.getSender());
        chatMessage.setType(MessageType.JOIN);
        return chatMessage;
    }


    @RequestMapping("/chat/openChat")
    public String test(){
        return "/chat/openChat";
    }

}
