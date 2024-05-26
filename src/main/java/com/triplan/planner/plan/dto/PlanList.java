package com.triplan.planner.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PlanList {

    private int count;
    private List<Schedule> scheduleList;
    //private Map<Long, List<String>> placeList;
}
