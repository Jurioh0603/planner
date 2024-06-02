package com.triplan.planner.mypage.repository;

import com.triplan.planner.mypage.dto.MyCommunityList;
import com.triplan.planner.mypage.dto.MyTlogList;
import com.triplan.planner.mypage.dto.Profile;

import java.util.List;

public interface MyPageRepository {

    public void updateProfile(Profile profile);

    public Profile getProfileList(String memberId);

    public void updateInfo(Profile profile);

    public List<MyTlogList> myFavList(String memberId, int startRow, int size);

    public int getCount();

    public int favCount(String memberId);

    public List<MyTlogList> myTlogList(String memberId, int i, int size);

    List<MyCommunityList> myComList(String memberId, int startRow, int size);
}