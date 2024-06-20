package com.triplan.planner.community.repository;

import com.triplan.planner.community.domain.Community;
import com.triplan.planner.community.dto.CommunityDetail;
import com.triplan.planner.community.dto.CommunityList;

public interface CommunityRepository {

    public CommunityList getCommunityList(String local, int startRow, int size, String search);
    public int getCount(String local, String search);

    public long insertCommunity(Community community, String local);

    public Community getCommunityByNo(String local, long bno);

    public void updateCommunity(Community community, String local);

    public void deleteCommunity(String local, long bno);

    public CommunityDetail getCommunityDetail(String local, long bno);
}
