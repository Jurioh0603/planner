package com.triplan.planner.calendar.controller;

import com.triplan.planner.calendar.domain.DateDTO;
import com.triplan.planner.calendar.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    //달력페이지
    @GetMapping("/calPick")
    public String calPick(){
        return "calendar/calendarPage";
    }

    @Autowired
    private CalendarService calendarService;

    //날짜선택저장
    @PostMapping("/save")
    public String saveDate(@RequestParam("startDay") String startDay,
                           @RequestParam("endDay") String endDay, Model model) throws ParseException {
        String memberId = "id1";

        // SimpleDateFormat 설정 (여기서는 "yyyy-MM-dd" 형식 사용)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            // String을 Date로 변환
            Date startDate = formatter.parse(startDay);
            Date endDate = formatter.parse(endDay);

        DateDTO dateDTO = new DateDTO(startDate, endDate, memberId);
        calendarService.saveDate(dateDTO);

        model.addAttribute("startDay",startDay );
        model.addAttribute("endDay", endDay);
        return "calendar/regPlan";
    }

    @GetMapping("/regPlan")
    public String regPlanPage() {
        return "calendar/regPlan"; // schedule/confirmation.html 페이지로 포워딩
    }





}
