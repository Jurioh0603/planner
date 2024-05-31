package com.triplan.planner.community.controller;

import com.triplan.planner.community.domain.Reply;
import com.triplan.planner.community.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community/reply")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/write")
    public String write(@ModelAttribute Reply reply, @RequestParam("local") String local) {
        String memberId = "id1";
        reply.setMemberId(memberId);
        replyService.write(reply, local);
        return "redirect:/community/detail?local=" + local + "&no=" + reply.getBno();
    }

    @PostMapping("/rewrite")
    public String rewrite(@ModelAttribute Reply reply, @RequestParam("local") String local) {
        String memberId = "id1";
        reply.setMemberId(memberId);
        replyService.rewrite(reply, local);
        return "redirect:/community/detail?local=" + local + "&no=" + reply.getBno();
    }

}
