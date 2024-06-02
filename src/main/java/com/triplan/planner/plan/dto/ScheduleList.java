package com.triplan.planner.plan.dto;

import com.triplan.planner.plan.domain.DetailSchedule;
import com.triplan.planner.plan.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
public class ScheduleList {

    private Schedule schedule;
    @Setter
    private List<Date> travelDay;
    private List<DetailSchedule> detailScheduleList;

    public ScheduleList(Schedule schedule, List<DetailSchedule> detailScheduleList) {
        this.schedule = schedule;
        this.detailScheduleList = detailScheduleList;
    }
}
