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

import java.util.HashMap;
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
    public ResponseEntity<Map<String, Object>> updateMemberGrade(@RequestBody Map<String, Object> payload) {
        String memId = (String) payload.get("id");
        Object gradeObj = payload.get("grade");
        int updateGrade;

        try {
            if (gradeObj instanceof String) {
                updateGrade = Integer.parseInt((String) gradeObj); // 문자열을 정수로 변환
            } else if (gradeObj instanceof Integer) {
                updateGrade = (Integer) gradeObj; // 이미 정수인 경우
            } else {
                throw new IllegalArgumentException("Invalid grade type");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid grade format: " + gradeObj);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Invalid grade format");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        System.out.println("Received member ID: " + memId);
        System.out.println("Received new grade: " + updateGrade);

        try {
            memberService.updateMemberGrade(memId, updateGrade);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Member grade updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error updating member grade: " + e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error updating member grade: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}

