package com.triplan.planner.community.service;

import com.triplan.planner.community.domain.Community;

import java.util.List;

public interface CommunityService {

    public List<Community> getCommunityList(String local);
}
