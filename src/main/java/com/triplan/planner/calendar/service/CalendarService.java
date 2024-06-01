package com.triplan.planner.calendar.service;

import com.triplan.planner.calendar.dto.DateDTO;
import com.triplan.planner.calendar.dto.ModifyForm;

public interface CalendarService {

    public long saveDate(DateDTO dateDTO);

    public void modifyDay(ModifyForm modifyForm);
}
