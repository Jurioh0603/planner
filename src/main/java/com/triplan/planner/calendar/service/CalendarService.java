package com.triplan.planner.calendar.service;

import com.triplan.planner.calendar.dto.DateDTO;
import com.triplan.planner.calendar.dto.ModifyForm;
import com.triplan.planner.calendar.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;

    public long saveDate(DateDTO dateDTO){
        return calendarRepository.saveDate(dateDTO);
    }

    public void modifyDay(ModifyForm modifyForm) {
        Date startDay = modifyForm.getStartDay();
        Date endDay = modifyForm.getEndDay();

        long diffmodifyDays = (endDay.getTime() - startDay.getTime()) / 1000 / (24*60*60);
        System.out.println(diffmodifyDays);
        calendarRepository.modifyDay(modifyForm, diffmodifyDays);
    }
}