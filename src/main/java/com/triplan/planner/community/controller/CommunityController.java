package com.triplan.planner.community.controller;

import com.triplan.planner.community.domain.Community;
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
        if(local.isEmpty())
            local = "S";
        List<Community> communityList = communityService.getCommunityList(local);
        model.addAttribute("communityList", communityList);
        model.addAttribute("local", local);
        return "community/commuList";
    }

    @GetMapping("/write")
    public String write(@RequestParam("local") String local) {
        return "community/writeForm";
    }

    @PostMapping("write")
    public String write(@ModelAttribute("form") Community community) {
        System.out.println(community.getTitle());
        return "community/detail";
    }

    @GetMapping("/modify")
    public String modify() {
        return "community/modifyForm";
    }

    @GetMapping("/detail")
    public String detail() {
        return "community/commuDetail";
    }
}
