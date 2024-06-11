package com.triplan.planner.chat.repository;

import com.triplan.planner.chat.dto.ChatRoom;

public interface ChatRepository {
    public ChatRoom createChatRoom(String roomName);
}
