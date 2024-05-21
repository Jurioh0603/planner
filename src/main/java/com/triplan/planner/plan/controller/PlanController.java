package com.triplan.planner.plan.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan")
public class PlanController {

    @Value("${KAKAO_API_KEY}")
    String KAKAO_API_KEY;

    @GetMapping("/list")
    public String list() {
        return "plan/myPlanList";
    }

    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("KAKAO_API_KEY", KAKAO_API_KEY);
        return "plan/planForm";
    }
}
