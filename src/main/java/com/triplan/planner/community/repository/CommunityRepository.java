package com.triplan.planner.community.repository;

import com.triplan.planner.community.domain.Community;
import com.triplan.planner.community.dto.CommunityDetail;

import java.util.List;

public interface CommunityRepository {

    public List<Community> getCommunityList(String local);

    public long insertCommunity(Community community, String local);

    public Community getCommunityByNo(String local, long bno);

    public void updateCommunity(Community community, String local);

    public void deleteCommunity(String local, long bno);

    public CommunityDetail getCommunityDetail(String local, long bno);
}
