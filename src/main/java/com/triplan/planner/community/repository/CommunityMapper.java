package com.triplan.planner.community.repository;

import com.triplan.planner.community.domain.Community;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper {

    public List<Community> getCommunityList(String local);

    public void insertCommunity(Community community, String local);

    public Community getCommunityByNo(String local, long bno);

    public void updateCommunity(Community community, String local);

    public void deleteCommunityByNo(String local, long bno);
}
