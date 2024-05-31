package com.triplan.planner.community.repository;

import com.triplan.planner.community.domain.Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {

    private final ReplyMapper replyMapper;

    @Override
    public void insertReply(Reply reply, String local) {
        local += "_reply";
        Long lastRstep = replyMapper.maxRstep(reply.getBno(), local);
        long rstep = 1;
        if(lastRstep != null)
            rstep = lastRstep + 1;
        replyMapper.insertReply(reply, rstep, local);
    }

    @Override
    public void insertReReply(Reply reply, String local) {
        local += "_reply";
        replyMapper.insertReReply(reply, local);
    }
}
