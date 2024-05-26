package com.triplan.planner.plan.repository;

import com.triplan.planner.plan.dto.DetailSchedule;
import com.triplan.planner.plan.dto.PlanList;
import com.triplan.planner.plan.dto.Schedule;
import com.triplan.planner.plan.dto.ScheduleList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlanRepositoryImpl implements PlanRepository {

    private final PlanMapper planMapper;

    public PlanList findPlanList(String memberId) {
        int count = planMapper.getCount(memberId);
        List<Schedule> scheduleList = planMapper.getSchedules(memberId);

        return new PlanList(count, scheduleList);
    }

    public ScheduleList findScheduleList(Long scheduleNo) {
        Schedule schedule = planMapper.getScheduleByNo(scheduleNo);
        List<DetailSchedule> detailScheduleList = planMapper.getDetailSchedules(scheduleNo);
        return new ScheduleList(schedule, detailScheduleList);
    }
}
