package com.triplan.planner.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class PlanList {

    private int count;
    private List<Schedule> scheduleList;
    private Map<Long, List<String>> placeList;
    private List<imageUploadForm> imageList;
}
