package com.triplan.planner.community.service;

import com.triplan.planner.community.domain.Reply;

public interface ReplyService {

    public void write(Reply reply, String local);

    public void rewrite(Reply reply, String local);

    public void modify(Reply reply, String local);

    public void delete(long rno, String local);
}
