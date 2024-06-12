package com.triplan.planner.chat.repository;

import com.triplan.planner.chat.dto.ChatRoom;

import java.util.List;

public interface ChatRepository {
    public ChatRoom createChatRoom(String roomName);

    public List<ChatRoom> findAllRoom();

    public ChatRoom findRoomById(String roomId);

    public void plusUserCnt(String roomId);
}
