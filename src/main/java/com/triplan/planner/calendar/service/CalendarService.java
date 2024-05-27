package com.triplan.planner.calendar.service;

import com.triplan.planner.calendar.domain.DateDTO;
import com.triplan.planner.calendar.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;

    public void saveDate(DateDTO dateDTO){
        calendarRepository.saveDate(dateDTO);
    }
}
