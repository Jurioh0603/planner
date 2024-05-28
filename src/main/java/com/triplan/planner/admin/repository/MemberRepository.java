package com.triplan.planner.admin.repository;

import com.triplan.planner.admin.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberRepository {
    //모든 회원 조회하는 쿼리문 실행하여 Member객체의 리스트로 반환
    List<Member> selectAll();

    //DB에서 특정 회원의 정보 업데이트(수정)하는 쿼리문 실행
    //@Param: 메서드 파라미터를 sql쿼리의 매개변수로 매핑 시 사용
    void updateMember(@Param("memberId") String memberId,
                      @Param("nickName") String nickName,
                      @Param("grade") int grade);
}
