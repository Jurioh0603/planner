package com.triplan.planner.chat.service;

import com.triplan.planner.chat.dto.ChatRoom;
import com.triplan.planner.chat.repository.ChatRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepositoryImpl chatRepositoryImpl;

    public ChatRoom createChatRoom(String roomName) {
        return ChatRoom;
    }
}