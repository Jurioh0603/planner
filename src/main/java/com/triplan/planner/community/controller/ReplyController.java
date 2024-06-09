package com.triplan.planner.community.controller;

import com.triplan.planner.community.domain.Reply;
import com.triplan.planner.community.service.ReplyService;
import com.triplan.planner.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community/reply")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/write")
    public String write(@ModelAttribute Reply reply, @RequestParam("local") String local, @SessionAttribute("loginMemberInfo") UserDto loginInfo) {
        String memberId = loginInfo.getMemberId();
        reply.setMemberId(memberId);
        //댓글 -> rstep 1
        reply.setRstep(1);
        replyService.write(reply, local);
        return "redirect:/community/detail?local=" + local + "&no=" + reply.getBno();
    }

    @PostMapping("/rewrite")
    public String rewrite(@ModelAttribute Reply reply, @RequestParam("local") String local, @SessionAttribute("loginMemberInfo") UserDto loginInfo) {
        String memberId = loginInfo.getMemberId();
        reply.setMemberId(memberId);
        //대댓글 -> rstep 2
        reply.setRstep(2);
        replyService.rewrite(reply, local);
        return "redirect:/community/detail?local=" + local + "&no=" + reply.getBno();
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Reply reply, @RequestParam("local") String local) {
        replyService.modify(reply, local);
        return "redirect:/community/detail?local=" + local + "&no=" + reply.getBno();
    }

    @PostMapping("delete")
    public String delete(@RequestParam("rno") long rno, @RequestParam("bno") long bno, @RequestParam("local") String local) {
        replyService.delete(rno, local);
        return "redirect:/community/detail?local=" + local + "&no=" + bno;
    }

}
