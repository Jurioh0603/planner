package com.triplan.planner.admin.service;

import com.triplan.planner.admin.domain.Community;
import com.triplan.planner.admin.repository.CommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    public List<Community> getPostsByCategory(String category) {
        return communityMapper.getPostsByCategory(category);
    }

    public List<Community> getPagedPostsByCategory(String category, String searchType, String searchQuery, int startRow, int size) {
        Map<String, Object> params = Map.of(
                "category", category,
                "searchType", searchType,
                "searchQuery", searchQuery,
                "startRow", startRow,
                "size", size
        );
        return communityMapper.getPagedPostsByCategory(params);
    }

    public int getCountByCategory(String category, String searchType, String searchQuery) {
        Map<String, Object> params = Map.of(
                "category", category,
                "searchType", searchType,
                "searchQuery", searchQuery
        );
        return communityMapper.getCountByCategory(params);
    }

    public void deletePosts(List<Integer> boardIdxArray, String category) {
        Map<String, Object> params = Map.of(
                "boardIdxArray", boardIdxArray,
                "category", category
        );
        communityMapper.deletePosts(params);
    }

    public List<Community> searchPosts(String category, String searchType, String searchQuery) {
        Map<String, Object> params = Map.of(
                "category", category,
                "searchType", searchType,
                "searchQuery", searchQuery
        );
        return communityMapper.searchPosts(params);
    }
}
