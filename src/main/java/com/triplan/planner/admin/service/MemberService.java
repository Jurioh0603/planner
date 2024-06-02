package com.triplan.planner.admin.service;

import com.triplan.planner.admin.dto.MemberDTO;

import java.util.List;

public interface MemberService {
    List<MemberDTO> getMembers(int page, int pageSize);
    int getMemberCount();
    List<MemberDTO> searchMembers(String searchType, String searchQuery, int page, int pageSize);
    int countSearchMembers(String searchType, String searchQuery);
    void updateMemberGrade(String memId, int grade);
}
