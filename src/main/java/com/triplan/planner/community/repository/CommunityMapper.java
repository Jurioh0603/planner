package com.triplan.planner.community.repository;

import com.triplan.planner.community.domain.Community;
import com.triplan.planner.community.dto.Profile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper {

    public List<Community> getCommunityList(String local, int startRow, int size, String search);
    public String getNickname(String memberId);
    public int getCountCommunity(String local, String search);

    public void insertCommunity(Community community, String local);

    public Community getCommunityByNo(String local, long bno);
    public Profile getWriterProfile(String memberId);

    public void updateCommunity(Community community, String local);

    public void deleteCommunityByNo(String local, long bno);
}
