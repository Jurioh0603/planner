package com.triplan.planner.admin.mapper;

import com.triplan.planner.admin.domain.Community;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommunityMapper {

    List<Community> getPostsByCategory(@Param("category") String category);

    List<Community> getPagedPostsByCategory(@Param("params") Map<String, Object> params);

    int getCountByCategory(@Param("params") Map<String, Object> params);
}
