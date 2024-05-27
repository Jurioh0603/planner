package com.triplan.planner.calendar.repository;

import com.triplan.planner.calendar.domain.DateDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CalendarMapper {

    public void saveDate(DateDTO dateDTO);

}
