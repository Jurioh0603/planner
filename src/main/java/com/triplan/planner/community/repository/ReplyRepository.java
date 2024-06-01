package com.triplan.planner.community.repository;

import com.triplan.planner.community.domain.Reply;

public interface ReplyRepository {

    public void insertReply(Reply reply, String local);

    public void insertReReply(Reply reply, String local);

    public void updateReply(Reply reply, String local);

    public void deleteReply(long rno, String local);
}
