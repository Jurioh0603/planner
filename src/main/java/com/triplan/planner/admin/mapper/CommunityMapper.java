package com.triplan.planner.admin.mapper;

import com.triplan.planner.admin.domain.Community;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityMapper {

    List<Community> getPostsByCategory(@Param("category") String category);

    List<Community> getPagedPostsByCategory(@Param("category") String category, @Param("startRow") int startRow, @Param("size") int size);

    int getCountByCategory(@Param("category") String category);

    int deletePosts(@Param("boardIdxList") List<String> boardIdxList, @Param("category") String category);

    List<Community> searchPosts(@Param("searchType") String searchType, @Param("searchQuery") String searchQuery);


}
