package com.triplan.planner.calendar.controller;

import com.triplan.planner.calendar.dto.DateDTO;
import com.triplan.planner.calendar.dto.ModifyForm;
import com.triplan.planner.calendar.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/plan")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    //달력페이지
    @GetMapping("/calendar")
    public String calPick(){
        return "calendar/calendarPage";
    }

    //날짜선택저장
    @PostMapping("/make")
    public String saveDate(@RequestParam("startDay") String startDay,
                           @RequestParam("endDay") String endDay,
                           @RequestParam("title") String title, Model model) throws ParseException {
        String memberId = "id1";

        // SimpleDateFormat 설정 (여기서는 "yyyy-MM-dd" 형식 사용)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // String을 Date로 변환
        Date startDate = formatter.parse(startDay);
        Date endDate = formatter.parse(endDay);

        DateDTO dateDTO = new DateDTO(startDate, endDate, memberId, title);
        long scheduleNo = calendarService.saveDate(dateDTO);

        return "redirect:/plan/write?no=" + scheduleNo;
    }

    @GetMapping("/modifyDay")
    public String modifyDay(@ModelAttribute("no") Long no) {
        return "calendar/modifyPage";
    }

    @PostMapping("/modifyDay")
    public String modifyDay(@RequestParam("startDay") String startDay,
                            @RequestParam("endDay") String endDay,
                            @RequestParam("no") Long scheduleNo) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = formatter.parse(startDay);
        Date endDate = formatter.parse(endDay);

        ModifyForm modifyForm = new ModifyForm(scheduleNo, startDate, endDate);
        calendarService.modifyDay(modifyForm);
        return "redirect:/plan/list";
    }

}