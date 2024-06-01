package com.triplan.planner.calendar.repository;

import com.triplan.planner.calendar.dto.DateDTO;
import com.triplan.planner.calendar.dto.ModifyForm;

public interface CalendarRepository {

    public long saveDate(DateDTO dateDTO);

    public void modifyDay(ModifyForm modifyForm, long diffmodifyDays);
}
