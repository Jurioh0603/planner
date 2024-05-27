package com.triplan.planner.calendar.repository;

import com.triplan.planner.calendar.domain.DateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class CalendarRepository {

    @Autowired
    CalendarMapper calendarMapper;

    public void saveDate(DateDTO dateDTO){
        calendarMapper.saveDate(dateDTO);
    }
}
