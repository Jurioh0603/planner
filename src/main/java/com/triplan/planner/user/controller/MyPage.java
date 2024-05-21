package com.triplan.planner.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MyPage {

    @GetMapping("/com/triplan/planner/user/myPage")
    public String MyPageForm(){
        return "com/triplan/planner/user/myPage";
    }
}