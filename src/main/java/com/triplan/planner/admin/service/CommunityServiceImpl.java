package com.triplan.planner.admin.service;

import com.triplan.planner.admin.domain.Community;
import com.triplan.planner.admin.repository.CommunityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityMapper communityMapper;

    @Override
    public List<Community> getPostsByCategory(String category) {
        return communityMapper.getPostsByCategory(category);
    }

    @Override
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

    @Override
    public int getCountByCategory(String category, String searchType, String searchQuery) {
        Map<String, Object> params = Map.of(
                "category", category,
                "searchType", searchType,
                "searchQuery", searchQuery
        );
        return communityMapper.getCountByCategory(params);
    }

    @Override
    public void deletePosts(List<Integer> boardIdxArray, String category) {
        Map<String, Object> params = Map.of(
                "boardIdxArray", boardIdxArray,
                "category", category
        );
        communityMapper.deletePosts(params);
    }

    @Override
    public List<Community> searchPosts(String category, String searchType, String searchQuery) {
        Map<String, Object> params = Map.of(
                "category", category,
                "searchType", searchType,
                "searchQuery", searchQuery
        );
        return communityMapper.searchPosts(params);
    }
}
