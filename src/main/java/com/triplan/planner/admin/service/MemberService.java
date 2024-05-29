// src/main/java/com/triplan/planner/admin/service/MemberService.java
package com.triplan.planner.admin.service;

import com.triplan.planner.admin.domain.Member;
import com.triplan.planner.admin.dto.MemberDTO;
import com.triplan.planner.admin.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 서버 콘솔 출력용
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    public List<MemberDTO> getAllMember() {
        List<Member> members = memberRepository.selectAll();
        return members.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MemberDTO convertToDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setMemId(member.getMemId());
        dto.setName(member.getName());
        dto.setNickName(member.getNickName());
        dto.setEmail(member.getEmail());
        dto.setGender(member.getGender());
        dto.setTel(member.getTel());
        dto.setGrade(member.getGrade());
        return dto;
    }

    public void updateMemberGrade(String memId, int grade) {
        System.out.println("Updating member ID: " + memId + " to grade: " + grade);
        if (grade == 2222) {
            memberRepository.deleteMember(memId);
        } else {
            memberRepository.updateMemberGrade(memId, grade);
        }
    }
}
