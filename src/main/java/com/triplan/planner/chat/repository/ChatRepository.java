package com.triplan.planner.chat.repository;

import com.triplan.planner.chat.dto.ChatRoom;

import java.util.List;

public interface ChatRepository {
    public void createChatRoom(String roomName);

    public List<ChatRoom> findAllRoom();

    public ChatRoom findRoomById(String roomId);
}
