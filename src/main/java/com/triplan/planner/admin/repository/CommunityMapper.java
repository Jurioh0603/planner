package com.triplan.planner.admin.repository;

import com.triplan.planner.admin.domain.Community;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommunityMapper {

    List<Community> getPostsByCategory(@Param("category") String category);

    List<Community> getPagedPostsByCategory(Map<String, Object> params);

    int getCountByCategory(Map<String, Object> params);

    void deletePosts(Map<String, Object> params);

    List<Community> searchPosts(Map<String, Object> params);
}
