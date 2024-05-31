package com.triplan.planner.mypage.service;

import com.triplan.planner.mypage.dto.Profile;
import com.triplan.planner.mypage.repository.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
