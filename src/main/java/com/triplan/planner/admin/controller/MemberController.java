package com.triplan.planner.admin.controller;

import com.triplan.planner.admin.dto.MemberDTO;
import com.triplan.planner.admin.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

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

    //회원정보수정
    @PostMapping("/updateMemberGrade")
    public ResponseEntity<String> updateMemberGrade(@RequestBody Map<String, Object> payload) {
        String memId = (String) payload.get("id");
        int updateGrade = (int) payload.get("grade");

        System.out.println("Received member ID: " + memId);
        System.out.println("Received new grade: " + updateGrade);

        try {
            memberService.updateMemberGrade(memId, updateGrade);
            return ResponseEntity.ok("Member grade updated successfully");
        } catch (Exception e) {
            System.err.println("Error updating member grade: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating member grade: " + e.getMessage());
        }

    }
}
