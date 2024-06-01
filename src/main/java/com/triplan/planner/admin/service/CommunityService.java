package com.triplan.planner.admin.service;

import com.triplan.planner.admin.domain.Community;
import com.triplan.planner.admin.mapper.CommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    public List<Community> getPostsByCategory(String category) {
        return communityMapper.getPostsByCategory(category);
    }

    public List<Community> getPagedPostsByCategory(String category, int page, int size) {
        int startRow = (page - 1) * size;
        return communityMapper.getPagedPostsByCategory(category, startRow, size);
    }

    public int getCountByCategory(String category) {
        return communityMapper.getCountByCategory(category);
    }

    public void deletePosts(List<String> boardIdxArray, String category) {
        communityMapper.deletePosts(boardIdxArray, category);
    }

    public List<Community> searchPosts(String searchType, String searchQuery) {
        return communityMapper.searchPosts(searchType, searchQuery);
    }

}
