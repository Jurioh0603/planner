package com.triplan.planner.community.repository;

import com.triplan.planner.community.domain.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {

    public Long maxRstep(long bno, String local);
    public void insertReply(Reply reply, long rstep, String local);

    public List<Reply> getReplyListByNo(String local, long bno);

    public void insertReReply(Reply reply, String local);
}
