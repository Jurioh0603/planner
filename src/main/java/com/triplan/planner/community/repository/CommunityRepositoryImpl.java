package com.triplan.planner.community.repository;

import com.triplan.planner.community.domain.Community;
import com.triplan.planner.community.domain.Reply;
import com.triplan.planner.community.dto.CommunityDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepository {

    private final CommunityMapper communityMapper;
    private final ReplyMapper replyMapper;

    @Override
    public List<Community> getCommunityList(String local) {
        local += "_COMMUNITY";
        return communityMapper.getCommunityList(local);
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

        Community community = communityMapper.getCommunityByNo(communityName, bno);
        List<Reply> replyList = replyMapper.getReplyListByNo(replyName, bno);
        return new CommunityDetail(community, replyList);
    }
}
