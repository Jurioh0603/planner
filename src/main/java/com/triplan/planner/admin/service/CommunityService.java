package com.triplan.planner.admin.service;

import com.triplan.planner.admin.domain.Community;
import com.triplan.planner.admin.mapper.CommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    public List<Community> getPostsByCategory(String category) {
        return communityMapper.getPostsByCategory(category);
    }

    public List<Community> getPagedPostsByCategory(String category, int page, int size, String searchType, String searchQuery) {
        int startRow = (page - 1) * size;
        Map<String, Object> params = new HashMap<>();
        params.put("category", category);
        params.put("startRow", startRow);
        params.put("size", size);
        params.put("searchType", searchType);
        params.put("searchQuery", searchQuery);
        return communityMapper.getPagedPostsByCategory(params);
    }

    public int getCountByCategory(String category, String searchType, String searchQuery) {
        Map<String, Object> params = new HashMap<>();
        params.put("category", category);
        params.put("searchType", searchType);
        params.put("searchQuery", searchQuery);
        return communityMapper.getCountByCategory(params);
    }
}
