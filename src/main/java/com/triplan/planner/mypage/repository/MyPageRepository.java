package com.triplan.planner.mypage.repository;

import com.triplan.planner.mypage.dto.Profile;

public interface MyPageRepository {

    public void updateProfile(Profile profile);

    public Profile getProfileList(String memberId);

    public void updateInfo(Profile profile);
}
