package com.triplan.planner.chat.service;

import com.triplan.planner.chat.dto.ChatRoom;

import java.util.List;

public interface ChatService {

    public ChatRoom createChatRoom(String roomName);

    public List<ChatRoom> findAllRoom();

    public ChatRoom findRoomById(String roomId);

    public void plusUserCnt(String roomId);
}
