package com.triplan.planner.admin.controller;

import com.triplan.planner.admin.repository.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/adminPage")
    public String adPage() { return "admin/adminPage"; }

    @Autowired
    AdminMapper adminMapper;

    @GetMapping("/test")
    public String DBConnectionTest() {
        System.out.println("DBConnectionTest 호출");
        adminMapper.test();
        return "redirect:/";
    }




}
