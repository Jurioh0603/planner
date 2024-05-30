package com.triplan.planner.calendar.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ModifyForm {

    private long scheduleNo;
    private Date startDay;
    private Date endDay;

    public ModifyForm(long scheduleNo, Date startDay, Date endDay) {
        this.scheduleNo = scheduleNo;
        this.startDay = startDay;
        this.endDay = endDay;
    }
}
