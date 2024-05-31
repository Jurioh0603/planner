package com.triplan.planner.community.service;

import com.triplan.planner.community.domain.Reply;
import com.triplan.planner.community.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public void write(Reply reply, String local) {
        replyRepository.insertReply(reply, local);
    }

    @Override
    public void rewrite(Reply reply, String local) {
        replyRepository.insertReReply(reply, local);
    }
}
