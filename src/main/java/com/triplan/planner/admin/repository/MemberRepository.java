package com.triplan.planner.admin.repository;

import com.triplan.planner.admin.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberRepository {
    // 모든 회원 조회
    List<Member> selectAll(@Param("offset") int offset, @Param("limit") int limit);

    // 회원 수 조회
    int countMembers();

    // 회원 정보 수정
    void updateMember(@Param("memberId") String memberId,
                      @Param("nickName") String nickName,
                      @Param("grade") int grade);

    // 특정 회원 조회
    Member selectById(@Param("memberId") String memberId);

    // 회원 등급 업데이트
    void updateMemberGrade(@Param("memId") String memId, @Param("grade") int grade);

    // 회원 검색
    List<Member> searchMembers(@Param("searchType") String searchType, @Param("searchQuery") String searchQuery, @Param("offset") int offset, @Param("limit") int limit);

    // 검색된 회원 수 조회
    int countSearchMembers(@Param("searchType") String searchType, @Param("searchQuery") String searchQuery);
}
