package com.triplan.planner.admin.controller;

import com.triplan.planner.admin.dto.MemberDTO;
import com.triplan.planner.admin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @GetMapping("/memberPage")
    public String memberPage(@RequestParam(value = "searchType", required = false) String searchType,
                             @RequestParam(value = "searchQuery", required = false) String searchQuery,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             Model model) {
        int pageSize = 10; // 한 페이지에 보여줄 회원 수
        List<MemberDTO> members;
        int totalMembers;

        if (searchType != null && searchQuery != null) {
            members = memberService.searchMembers(searchType, searchQuery, page, pageSize);
            totalMembers = memberService.countSearchMembers(searchType, searchQuery);
        } else {
            members = memberService.getMembers(page, pageSize);
            totalMembers = memberService.getMemberCount();
        }

        int totalPages = (int) Math.ceil((double) totalMembers / pageSize);
        model.addAttribute("member", members);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchQuery", searchQuery);

        return "admin/memberPage";
    }

    // 회원정보수정
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
            logger.error("Invalid grade format: {}", gradeObj);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Invalid grade format");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            memberService.updateMemberGrade(memId, updateGrade);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Member grade updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error updating member grade: {}", e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error updating member grade: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
