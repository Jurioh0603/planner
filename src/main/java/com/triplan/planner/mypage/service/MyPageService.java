package com.triplan.planner.mypage.service;

import com.triplan.planner.mypage.dto.Profile;

public interface MyPageService {


    public void updateProfile(Profile profile);

    public Profile getProfileList(String memberId);

    public void updateInfo(Profile profile);
}
