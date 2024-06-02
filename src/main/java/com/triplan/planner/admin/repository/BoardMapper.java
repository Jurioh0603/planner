package com.triplan.planner.admin.repository;

import com.triplan.planner.admin.domain.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    List<Board> getPostsByCategory(@Param("category") String category);

    List<Board> getPagedPostsByCategory(Map<String, Object> params);

    int getCountByCategory(Map<String, Object> params);

    void deletePosts(Map<String, Object> params);

    List<Board> searchPosts(Map<String, Object> params);
}
