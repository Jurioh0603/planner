package com.triplan.planner.community.service;

import com.triplan.planner.community.domain.Community;
import com.triplan.planner.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    @Override
    public List<Community> getCommunityList(String local) {
        return communityRepository.getCommunityList(local);
    }
}
