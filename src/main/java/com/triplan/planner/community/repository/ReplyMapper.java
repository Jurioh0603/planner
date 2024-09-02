package com.triplan.planner.community.repository;

import com.triplan.planner.community.domain.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {

    public Long maxRef(long bno, String local);
    public void insertReply(Reply reply, long ref, String local);

    public List<Reply> getReplyListByNo(String local, long bno);

    public void insertReReply(Reply reply, String local);

    public void updateReply(Reply reply, String local);

    public Reply getReplyByNo(long rno, String local);
    public List<Reply> getReplyListByRef(long bno, long ref, String local);

    public void updateToDeleteReply(long rno, String local);
    public void deleteReplyByRef(long bno, long ref, String local);
    public void deleteReplyByNo(long rno, String local);
}
