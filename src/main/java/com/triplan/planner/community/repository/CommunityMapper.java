package com.triplan.planner.community.repository;

import com.triplan.planner.community.domain.Community;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper {

    public List<Community> getCommunityList(String local);
}
