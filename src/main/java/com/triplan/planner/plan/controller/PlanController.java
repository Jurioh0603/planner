package com.triplan.planner.plan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan")
public class PlanController {

    @GetMapping("/list")
    public String list() {
        return "plan/myPlanList";
    }
}
