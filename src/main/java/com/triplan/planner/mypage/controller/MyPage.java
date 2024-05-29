package com.triplan.planner.mypage.controller;

import com.triplan.planner.mypage.dto.Profile;
import com.triplan.planner.mypage.service.MyPageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPage {

    @Autowired
    private MyPageService myPageService;

    @GetMapping("/mypage/myPage")
    public String MyPageForm(Profile profile,
                             Model model,
                             HttpSession session){
        String memberId = "id1";
        session.setAttribute("memberId", memberId);
        session.getAttribute("memberId");
        myPageService.selectProfile(memberId);
        model.addAttribute("memberId", memberId);
        model.addAttribute("profile", profile);
        return "/mypage/myPage";
    }
}