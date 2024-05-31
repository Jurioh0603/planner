package com.triplan.planner.community.controller;

import com.triplan.planner.community.domain.Community;
import com.triplan.planner.community.dto.CommunityDetail;
import com.triplan.planner.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/list")
    public String list(@ModelAttribute("local") String local, Model model) {
        //local 파라미터 없이 접근 시 기본값 S(서울)
        if(local.isEmpty())
            local = "S";
        List<Community> communityList = communityService.getCommunityList(local);
        model.addAttribute("communityList", communityList);
        model.addAttribute("local", local);
        return "community/commuList";
    }

    @GetMapping("/write")
    public String write(@RequestParam("local") String local, Model model) {
        model.addAttribute("local", local);
        return "community/writeForm";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Community community, @RequestParam("local") String local) {
        String memberId = "id1";
        community.setMemberId(memberId);
        long bno = communityService.write(community, local);
        return "redirect:/community/detail?local=" + local + "&no=" + bno;
    }

    @GetMapping("/modify")
    public String modify(@RequestParam("local") String local, @RequestParam("no") long bno, Model model) {
        Community community = communityService.getCommunity(local, bno);
        model.addAttribute("community", community);
        model.addAttribute("local", local);
        return "community/modifyForm";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Community community, @RequestParam("local") String local) {
        communityService.modify(community, local);
        return "redirect:/community/detail?local=" + local + "&no=" + community.getBno();
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("local") String local, @RequestParam("no") long bno, Model model) {
        CommunityDetail communityDetail = communityService.getCommunityDetail(local, bno);
        model.addAttribute("communityDetail", communityDetail);
        model.addAttribute("local", local);
        return "community/commuDetail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("local") String local, @RequestParam("no") long bno, Model model) {
        communityService.deleteCommunity(local, bno);
        return "redirect:/community/list?local=" + local;
    }
}
