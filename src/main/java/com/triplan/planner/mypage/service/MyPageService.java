package com.triplan.planner.mypage.service;

import com.triplan.planner.mypage.repository.MyPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {

    @Autowired
    private MyPageMapper myPageMapper;

    public void selectProfile(String memberId) {
        myPageMapper.selectProfile(memberId);
    }
}
