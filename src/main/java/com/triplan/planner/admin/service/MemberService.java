package com.triplan.planner.admin.service;

import com.triplan.planner.admin.domain.Member;
import com.triplan.planner.admin.dto.MemberDTO;
import com.triplan.planner.admin.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {


    @Autowired
    private MemberRepository memberRepository;

    public List<MemberDTO> getAllMember(){
        List<Member> member = memberRepository.selectAll();


        return member.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MemberDTO convertToDTO(Member member){
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
}
