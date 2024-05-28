package com.triplan.planner.plan.repository;

import com.triplan.planner.plan.domain.DetailSchedule;
import com.triplan.planner.plan.dto.PlanList;
import com.triplan.planner.plan.domain.ScheduleImage;
import com.triplan.planner.plan.dto.ScheduleList;

import java.util.List;

public interface PlanRepository {

    public PlanList findPlanList(String memberId);
    public ScheduleList findScheduleList(Long scheduleNo);
    public void save(ScheduleImage scheduleImage);
    public void deleteScheduleByNo(Long scheduleNo);

    public void insertDetailSchedules(List<DetailSchedule> detailScheduleList);
}
