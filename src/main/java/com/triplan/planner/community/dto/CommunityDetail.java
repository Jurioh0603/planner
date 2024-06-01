package com.triplan.planner.community.dto;

import com.triplan.planner.community.domain.Community;
import com.triplan.planner.community.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommunityDetail {

    private Community community;
    private List<Reply> replyList;
    private Profile writerProfile;
    private List<String> replyNicknameList;
}
