package com.triplan.planner.mypage.repository;

import com.triplan.planner.mypage.dto.Profile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageMapper {
    public void updateProfile(Profile profile);
    public Profile getProfileList(String memberId);
    public void updateInfo(Profile profile);
}
