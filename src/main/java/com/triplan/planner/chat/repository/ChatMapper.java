package com.triplan.planner.chat.repository;

import com.triplan.planner.chat.dto.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    public void createChatRoom(ChatRoom chatRoom);

    public List<ChatRoom> findAllRoom();

    public ChatRoom findRoomById(String roomId);

    public void plusUserCnt(ChatRoom chatRoom);
}
