package com.triplan.planner.community.repository;

import com.triplan.planner.community.domain.Community;
import com.triplan.planner.community.domain.Reply;
import com.triplan.planner.community.dto.CommunityDetail;
import com.triplan.planner.community.dto.CommunityList;
import com.triplan.planner.community.dto.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepository {

    private final CommunityMapper communityMapper;
    private final ReplyMapper replyMapper;

    @Override
    public CommunityList getCommunityList(String local, int startRow, int size, String search) {
        local += "_COMMUNITY";
        List<Community> communityList = communityMapper.getCommunityList(local, startRow, size, search);
        //id -> nickname 변환
        List<String> nicknameList = new ArrayList<>();
        if(!communityList.isEmpty()) {
            for (int i = 0; i < communityList.size(); i++) {
                String nickname = communityMapper.getNickname(communityList.get(i).getMemberId());
                nicknameList.add(nickname);
            }
        }
        return new CommunityList(communityList, nicknameList);
    }

    @Override
    public int getCount(String local, String search) {
        local += "_COMMUNITY";
        return communityMapper.getCountCommunity(local, search);
    }

    @Override
    public long insertCommunity(Community community, String local) {
        local += "_COMMUNITY";
        communityMapper.insertCommunity(community, local);
        return community.getBno();
    }

    @Override
    public Community getCommunityByNo(String local, long bno) {
        local += "_COMMUNITY";
        return communityMapper.getCommunityByNo(local, bno);
    }

    @Override
    public void updateCommunity(Community community, String local) {
        local += "_COMMUNITY";
        communityMapper.updateCommunity(community, local);
    }

    @Override
    public void deleteCommunity(String local, long bno) {
        local += "_COMMUNITY";
        communityMapper.deleteCommunityByNo(local, bno);
    }

    @Override
    public CommunityDetail getCommunityDetail(String local, long bno) {
        String communityName = local + "_COMMUNITY";
        String replyName = local + "_REPLY";

        //작성글 관련
        Community community = communityMapper.getCommunityByNo(communityName, bno);
        Profile writerProfile = communityMapper.getWriterProfile(community.getMemberId());

        //댓글 관련
        List<Reply> replyList = replyMapper.getReplyListByNo(replyName, bno);
        List<String> nicknameList = new ArrayList<>();
        if(!replyList.isEmpty()) {
            for (int i = 0; i < replyList.size(); i++) {
                String nickname = communityMapper.getNickname(replyList.get(i).getMemberId());
                nicknameList.add(nickname);
            }
        }
        return new CommunityDetail(community, replyList, writerProfile, nicknameList);
    }
}
