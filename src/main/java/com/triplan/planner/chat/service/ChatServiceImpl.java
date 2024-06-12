package com.triplan.planner.chat.service;

import com.triplan.planner.chat.dto.ChatRoom;
import com.triplan.planner.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final ChatRepository chatRepository;

    public void createChatRoom(String roomName) {
        chatRepository.createChatRoom(roomName);
    }

    @Override
    public List<ChatRoom> findAllRoom() {
        return chatRepository.findAllRoom();
    }

    @Override
    public ChatRoom findRoomById(String roomId) {
        return chatRepository.findRoomById(roomId);
    }
}