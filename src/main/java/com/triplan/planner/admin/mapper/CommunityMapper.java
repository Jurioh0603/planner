package com.triplan.planner.admin.mapper;

import com.triplan.planner.admin.domain.Community;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityMapper {

    List<Community> getPostsByCategory(@Param("category") String category);
}
