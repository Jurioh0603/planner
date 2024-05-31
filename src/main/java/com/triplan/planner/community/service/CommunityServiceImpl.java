package com.triplan.planner.community.service;

import com.triplan.planner.community.domain.Community;
import com.triplan.planner.community.dto.CommunityDetail;
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

    @Override
    public long write(Community community, String local) {
        return communityRepository.insertCommunity(community, local);
    }

    @Override
    public Community getCommunity(String local, long bno) {
        return communityRepository.getCommunityByNo(local, bno);
    }

    @Override
    public void modify(Community community, String local) {
        communityRepository.updateCommunity(community, local);
    }

    @Override
    public void deleteCommunity(String local, long bno) {
        communityRepository.deleteCommunity(local, bno);
    }

    @Override
    public CommunityDetail getCommunityDetail(String local, long bno) {
        return communityRepository.getCommunityDetail(local, bno);
    }
}
