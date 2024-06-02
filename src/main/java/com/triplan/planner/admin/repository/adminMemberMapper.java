package com.triplan.planner.admin.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface adminMemberMapper {
    void updateMemberGrade(Map<String, Object> params);
}
