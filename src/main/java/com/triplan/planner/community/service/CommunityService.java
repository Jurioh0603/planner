package com.triplan.planner.community.service;

import com.triplan.planner.community.domain.Community;
import com.triplan.planner.community.dto.CommunityDetail;
import com.triplan.planner.community.dto.CommunityList;

public interface CommunityService {

    public CommunityList getCommunityList(String local, int page, String search);
    public int getCount(String local, String search);

    public long write(Community community, String local);

    public Community getCommunity(String local, long bno);

    public void modify(Community community, String local);

    public void deleteCommunity(String local, long bno);

    CommunityDetail getCommunityDetail(String local, long bno);

}
