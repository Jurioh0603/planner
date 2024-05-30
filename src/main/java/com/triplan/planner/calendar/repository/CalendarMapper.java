package com.triplan.planner.calendar.repository;

import com.triplan.planner.calendar.dto.DateDTO;
import com.triplan.planner.calendar.dto.ModifyForm;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CalendarMapper {

    public void saveDate(DateDTO dateDTO);

    public void deleteOverSchedule(long scheduleNo, long diffmodifyDays);
    public void updateDayByNo(ModifyForm modifyForm);
}