package com.triplan.planner.admin.controller;

import com.triplan.planner.admin.dto.MemberDTO;
import com.triplan.planner.admin.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService memberService;

    @GetMapping("/memberPage")
    public String memberPage(Model model){
        List<MemberDTO> member = memberService.getAllMember();
        model.addAttribute("member", member);

        logger.info("Model Member: {}", member);
        return "admin/memberPage";
    }
}
