package com.triplan.planner.tlog.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tlog")
public class TlogController {

    @Value("${KAKAO_API_KEY}")
    String KAKAO_API_KEY;

    @GetMapping("/list")
    public String list() {
        return "tlog/tlogList";
    }

    @GetMapping("/write")
    public String write(Model model) {
        return "tlog/tlogWriteForm";
    }

    @GetMapping("/selectPlan")
    public String select() {
        return "tlog/addPlan";
    }

    @GetMapping("/detail")
    public String detail(Model model) {
        model.addAttribute("KAKAO_API_KEY", KAKAO_API_KEY);
        return "tlog/tlogDetail";
    }
}
