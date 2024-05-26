package com.triplan.planner.plan.controller;

import com.triplan.planner.plan.dto.PlanList;
import com.triplan.planner.plan.dto.ScheduleList;
import com.triplan.planner.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/plan")
public class PlanController {

    private final PlanService planService;

    @Value("${KAKAO_API_KEY}")
    String KAKAO_API_KEY;

    @GetMapping("/list")
    public String list(Model model) {
        String memberId = "id1"; //나중에 세션에서 로그인한 id를 가져옴
        PlanList planList = planService.getPlanList(memberId);

        model.addAttribute("planList", planList);

        return "plan/myPlanList";
    }

    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("KAKAO_API_KEY", KAKAO_API_KEY);
        return "plan/planForm";
    }

    @GetMapping("/modify")
    public String modify(@RequestParam("scheduleNo") Long scheduleNo, Model model) {
        ScheduleList scheduleList = planService.getScheduleList(scheduleNo);

        model.addAttribute("KAKAO_API_KEY", KAKAO_API_KEY);
        model.addAttribute("scheduleList", scheduleList);

        return "plan/modifyForm";
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
}
