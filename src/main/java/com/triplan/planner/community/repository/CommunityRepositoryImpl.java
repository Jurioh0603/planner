package com.triplan.planner.community.repository;

import com.triplan.planner.community.domain.Community;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepository {

    private final CommunityMapper communityMapper;

    @Override
    public List<Community> getCommunityList(String local) {
        local += "_COMMUNITY";
        return communityMapper.getCommunityList(local);
    }
}
