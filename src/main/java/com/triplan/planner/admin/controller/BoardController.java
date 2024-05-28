package com.triplan.planner.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BoardController {

    @GetMapping("/boardPage")
    public String boardPage(){
        return "admin/boardPage";
    }


}
