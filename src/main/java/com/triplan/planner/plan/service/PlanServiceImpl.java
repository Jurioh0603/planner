package com.triplan.planner.plan.service;

import com.triplan.planner.plan.dto.PlanList;
import com.triplan.planner.plan.domain.ScheduleImage;
import com.triplan.planner.plan.repository.PlanRepository;
import com.triplan.planner.plan.dto.ScheduleList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    public PlanList getPlanList(String memberId) {
        return planRepository.findPlanList(memberId);
    }

    public ScheduleList getScheduleList(Long scheduleNo) {
        ScheduleList scheduleList = planRepository.findScheduleList(scheduleNo);

        List<Date> travelDay = new ArrayList<>();
        Date startDay = scheduleList.getSchedule().getStartDay();
        Date endDay = scheduleList.getSchedule().getEndDay();
        Date day = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDay);
        do {
            day = calendar.getTime();
            travelDay.add(day);
            calendar.add(Calendar.DATE, 1);
        } while(endDay.compareTo(day) > 0);
        scheduleList.setTravelDay(travelDay);

        return scheduleList;
    }

    public void save(ScheduleImage scheduleImage) {
        planRepository.save(scheduleImage);
    }

    public void deletePlan(Long scheduleNo) {
        planRepository.deleteScheduleByNo(scheduleNo);
    }
}
