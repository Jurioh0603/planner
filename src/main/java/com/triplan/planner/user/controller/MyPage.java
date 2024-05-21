package com.triplan.planner.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MyPage {

    @GetMapping("/user/myPage")
    public String MyPageForm(){
        return "user/myPage";
    }
}
