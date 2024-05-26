package com.triplan.planner.plan.repository;

import com.triplan.planner.plan.dto.DetailSchedule;
import com.triplan.planner.plan.dto.Schedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanMapper {

    public int getCount(String memberId);
    public List<Schedule> getSchedules(String memberId);
    //public PlanList findList();

    public Schedule getScheduleByNo(Long scheduleNo);
    public List<DetailSchedule> getDetailSchedules(Long scheduleNo);

}
