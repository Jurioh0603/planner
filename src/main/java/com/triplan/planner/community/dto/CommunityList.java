package com.triplan.planner.community.dto;

import com.triplan.planner.community.domain.Community;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommunityList {

    private List<Community> communities;
    private List<String> nicknameList;
}
