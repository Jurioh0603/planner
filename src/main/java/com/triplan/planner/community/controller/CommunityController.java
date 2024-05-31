package com.triplan.planner.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/commu")
public class CommunityController {

    @GetMapping("/list")
    public String list() {
        return "community/commuList";
    }

    @GetMapping("/write")
    public String write() {
        return "community/writeForm";
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
