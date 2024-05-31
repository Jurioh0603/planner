package com.triplan.planner.community.service;

import com.triplan.planner.community.domain.Community;
import com.triplan.planner.community.dto.CommunityDetail;

import java.util.List;

public interface CommunityService {

    public List<Community> getCommunityList(String local);

    public long write(Community community, String local);

    public Community getCommunity(String local, long bno);

    public void modify(Community community, String local);

    public void deleteCommunity(String local, long bno);

    CommunityDetail getCommunityDetail(String local, long bno);
}
