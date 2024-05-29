package com.triplan.planner.calendar.repository;

import com.triplan.planner.calendar.dto.DateDTO;
import com.triplan.planner.calendar.dto.ModifyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CalendarRepository {

    @Autowired
    CalendarMapper calendarMapper;

    public void saveDate(DateDTO dateDTO){
        calendarMapper.saveDate(dateDTO);
    }

    public void modifyDay(ModifyForm modifyForm, long diffmodifyDays) {
        calendarMapper.deleteOverSchedule(modifyForm.getScheduleNo(), diffmodifyDays);
        calendarMapper.updateDayByNo(modifyForm);
    }
}