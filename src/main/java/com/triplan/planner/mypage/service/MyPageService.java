package com.triplan.planner.mypage.service;

import com.triplan.planner.mypage.dto.MyCommunityList;
import com.triplan.planner.mypage.dto.MyTlogList;
import com.triplan.planner.mypage.dto.Profile;

import java.util.List;

public interface MyPageService {


    public void updateProfile(Profile profile);

    public Profile getProfileList(String memberId);

    public void updateInfo(Profile profile);

    public List<MyTlogList> myFavList(String memberId, int page);

    public int tlogCount(String memberId);

    public int favCount(String memberId);

    public List<MyTlogList> myTlogList(String memberId, int page);

    public List<MyCommunityList> myComList(String memberId, int page);

    public int communityCount(String memberId);

    public void memberLeave(String memberId);
}
