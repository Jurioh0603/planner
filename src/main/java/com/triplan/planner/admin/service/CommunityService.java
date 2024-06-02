package com.triplan.planner.admin.service;

import com.triplan.planner.admin.domain.Community;

import java.util.List;

public interface CommunityService {
    List<Community> getPostsByCategory(String category);

    List<Community> getPagedPostsByCategory(String category, String searchType, String searchQuery, int startRow, int size);

    int getCountByCategory(String category, String searchType, String searchQuery);

    void deletePosts(List<Integer> boardIdxArray, String category);

    List<Community> searchPosts(String category, String searchType, String searchQuery);
}
