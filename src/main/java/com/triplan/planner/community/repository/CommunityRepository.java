package com.triplan.planner.community.repository;

import com.triplan.planner.community.domain.Community;

import java.util.List;

public interface CommunityRepository {

    public List<Community> getCommunityList(String local);
}
