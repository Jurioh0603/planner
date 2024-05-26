package com.triplan.planner.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    @GetMapping("/calPick")
    public String calPick(){
        return "calendar/calendarPage";
    }

}
