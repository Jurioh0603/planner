package com.triplan.planner.mypage.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageMapper {

    public void selectProfile (String memberId);
}
