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
}
