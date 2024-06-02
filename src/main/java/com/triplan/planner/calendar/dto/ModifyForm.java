package com.triplan.planner.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ModifyForm {

    private long scheduleNo;
    private Date startDay;
    private Date endDay;
}
