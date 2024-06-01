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
        Long lastRef = replyMapper.maxRef(reply.getBno(), local);
        long ref = 1;
        if(lastRef != null)
            ref = lastRef + 1;
        replyMapper.insertReply(reply, ref, local);
    }

    @Override
    public void insertReReply(Reply reply, String local) {
        local += "_reply";
        replyMapper.insertReReply(reply, local);
    }

    @Override
    public void updateReply(Reply reply, String local) {
        local += "_reply";
        replyMapper.updateReply(reply, local);
    }

    @Override
    public void deleteReply(long rno, String local) {
        local += "_reply";
        replyMapper.deleteReplyByNo(rno, local);
    }
}
