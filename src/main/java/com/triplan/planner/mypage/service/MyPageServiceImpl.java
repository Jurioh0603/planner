package com.triplan.planner.mypage.service;

import com.triplan.planner.mypage.dto.MyCommunityList;
import com.triplan.planner.mypage.dto.MyTlogList;
import com.triplan.planner.mypage.dto.Profile;
import com.triplan.planner.mypage.repository.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

    private final MyPageRepository myPageRepository;

    @Override
    public void updateProfile(Profile profile) {
        myPageRepository.updateProfile(profile);
    }

    @Override
    public Profile getProfileList(String memberId) {
        return myPageRepository.getProfileList(memberId);
    }

    @Override
    public void updateInfo(Profile profile) {
        myPageRepository.updateInfo(profile);
    }

    @Override
    public List<MyTlogList> myFavList(String memberId, int page) {
        int size = 6;
        return myPageRepository.myFavList(memberId, (page-1) * size, size);
    }

    @Override
    public int tlogCount(String memberId) {
        return myPageRepository.tlogCount(memberId);
    }

    @Override
    public int favCount(String memberId) {
        return myPageRepository.favCount(memberId);
    }

    @Override
    public List<MyTlogList> myTlogList(String memberId, int page) {
        int size = 6;
        return myPageRepository.myTlogList(memberId, (page-1) * size, size);
    }

    @Override
    public List<MyCommunityList> myComList(String memberId, int page) {
        int size = 6;
        return myPageRepository.myComList(memberId,(page - 1) * size, size);
    }

    @Override
    public int communityCount(String memberId) {
        return myPageRepository.communityCount(memberId);
    }

    @Override
    public void memberLeave(String memberId) {
        myPageRepository.memberLeave(memberId);
    }


}
