package com.triplan.planner.mypage.repository;

import com.triplan.planner.mypage.dto.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

}
