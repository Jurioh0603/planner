package com.triplan.planner.plan.controller;

import com.triplan.planner.plan.repository.PlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/addAttr")
    public String addAttr() {
        return "plan/addAttraction";
    }

    @GetMapping("/addMyPlace")
    public String addMyPlace(Model model) {
        model.addAttribute("KAKAO_API_KEY", KAKAO_API_KEY);
        return "plan/addMyPlace";
    }

    @GetMapping("/addOnMap")
    public String addOnMap(Model model) {
        model.addAttribute("KAKAO_API_KEY", KAKAO_API_KEY);
        return "plan/addOnMap";
    }

    @Autowired
    PlanMapper planMapper;

    @GetMapping("/test")
    public String DBConnectionTest() {
        System.out.println("DBConnectionTest 호출");
        planMapper.test();
        return "redirect:/";
    }
}
