package com.triplan.planner.plan.service;

import com.triplan.planner.plan.dto.PlanList;
import com.triplan.planner.plan.domain.ScheduleImage;
import com.triplan.planner.plan.dto.ScheduleList;

public interface PlanService {

    public PlanList getPlanList(String memberId);
    public ScheduleList getScheduleList(Long scheduleNo);
    public void save(ScheduleImage scheduleImage);
    public void deletePlan(Long scheduleNo);
}
