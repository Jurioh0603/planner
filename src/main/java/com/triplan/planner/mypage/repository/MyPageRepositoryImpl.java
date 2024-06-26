package com.triplan.planner.mypage.repository;

import com.triplan.planner.mypage.dto.MyCommunityList;
import com.triplan.planner.mypage.dto.MyTlogList;
import com.triplan.planner.mypage.dto.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyPageRepositoryImpl implements MyPageRepository{

    private final MyPageMapper myPageMapper;

    @Override
    public void updateProfile(Profile profile) {
        myPageMapper.updateProfile(profile);
    }

    @Override
    public Profile getProfileList(String memberId) {
        return myPageMapper.getProfileList(memberId);
    }

    @Override
    public void updateInfo(Profile profile) {
        myPageMapper.updateInfo(profile);
    }

    @Override
    public List<MyTlogList> myFavList(String memberId, int startRow, int size) {
        return myPageMapper.myFavList(memberId, startRow, size);
    }

    @Override
    public int tlogCount(String memberId) {
        return myPageMapper.tlogCount(memberId);
    }

    @Override
    public int favCount(String memberId) {
        return myPageMapper.favCount(memberId);
    }

    @Override
    public List<MyTlogList> myTlogList(String memberId, int startRow, int size) {
        return myPageMapper.myTlogList(memberId, startRow, size);
    }

    @Override
    public List<MyCommunityList> myComList(String memberId, int startRow, int size) {
        return myPageMapper.myComList(memberId, startRow, size);
    }

    @Override
    public int communityCount(String memberId) {
        return myPageMapper.communityCount(memberId);
    }

    @Override
    public void memberLeave(String memberId) {
        myPageMapper.memberLeave(memberId);
    }

    @Override
    public void defaultImg(String memberId) {
        myPageMapper.defaultImg(memberId);
    }
}
