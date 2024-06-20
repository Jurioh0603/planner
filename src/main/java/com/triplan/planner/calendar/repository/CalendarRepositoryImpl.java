package com.triplan.planner.calendar.repository;

import com.triplan.planner.calendar.dto.DateDTO;
import com.triplan.planner.calendar.dto.ModifyForm;
import com.triplan.planner.plan.repository.PlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CalendarRepositoryImpl implements CalendarRepository {

    private final CalendarMapper calendarMapper;
    private final PlanMapper planMapper;

    public long saveDate(DateDTO dateDTO){
        calendarMapper.saveDate(dateDTO);
        return planMapper.getLastScheduleNo();
    }

    public void modifyDay(ModifyForm modifyForm, long diffmodifyDays) {
        calendarMapper.deleteOverSchedule(modifyForm.getScheduleNo(), diffmodifyDays);
        calendarMapper.updateDayByNo(modifyForm);
    }
}