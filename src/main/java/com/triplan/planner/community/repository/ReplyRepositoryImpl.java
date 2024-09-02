package com.triplan.planner.community.repository;

import com.triplan.planner.community.domain.Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {

    private final ReplyMapper replyMapper;

    @Override
    public void insertReply(Reply reply, String local) {
        local += "_reply";
        Long lastRef = replyMapper.maxRef(reply.getBno(), local);
        //첫 댓글 -> ref 1
        long ref = 1;
        //첫 댓글X -> 가장 마지막 ref + 1
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
    public void deleteReply(long bno, long rno, String local) {
        local += "_reply";
        Reply reply = replyMapper.getReplyByNo(rno, local); //삭제할 댓글
        List<Reply> replyListByRef = replyMapper.getReplyListByRef(bno, reply.getRef(), local); //삭제할 댓글과 같은 그룹의 댓글들

        if (reply.getRstep() == 1 && replyListByRef.size() > 1) { //대댓글이 존재하는 댓글 삭제 -> '삭제된 댓글입니다'로 내용 변경
            replyMapper.updateToDeleteReply(rno, local);
        } else if (reply.getRstep() == 2 && replyListByRef.size() == 2 && replyListByRef.get(0).getRstep() == 0) { //같은 그룹의 모든 댓글 삭제
            replyMapper.deleteReplyByRef(bno, reply.getRef(), local);
        } else {
            replyMapper.deleteReplyByNo(rno, local);
        }
    }
}
