package com.triplan.planner.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String main(){return "/main/index";}

    @GetMapping("/error/forbidden")
    public String forbidden() {
        return "/error/403";
    }

    @GetMapping("/error/notFound")
    public String notFound() {
        return "/error/404";
    }
}
