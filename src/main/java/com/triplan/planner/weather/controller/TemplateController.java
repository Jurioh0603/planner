package com.triplan.planner.weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/templates")
public class TemplateController {

    @GetMapping("/dailyweather")
    public String template(){
        return "fragments/dailyweather";
    }

    @GetMapping("/weekTemp")
    public String layout(){
        return "fragments/weekTemp";
    }

    @GetMapping("/dropdown")
    public String header(){
        return "fragments/jquery-menu";
    }
}
