package com.triplan.planner.plan.repository;

import com.triplan.planner.plan.dto.PlanList;
import com.triplan.planner.plan.dto.ScheduleList;

public interface PlanRepository {

    public PlanList findPlanList(String memberId);
    public ScheduleList findScheduleList(Long scheduleNo);
}
