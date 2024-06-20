package com.triplan.planner.admin.service;

import com.triplan.planner.admin.domain.Board;

import java.util.List;

public interface BoardService {
    List<Board> getPostsByCategory(String category);

    List<Board> getPagedPostsByCategory(String category, String searchType, String searchQuery, int startRow, int size);

    int getCountByCategory(String category, String searchType, String searchQuery);

    void deletePosts(List<Integer> boardIdxArray, String category);

    List<Board> searchPosts(String category, String searchType, String searchQuery);
}
