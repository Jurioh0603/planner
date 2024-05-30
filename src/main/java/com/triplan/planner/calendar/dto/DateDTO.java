package com.triplan.planner.calendar.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DateDTO {

    private Date startDay;
    private Date endDay;
    private String memberId;
    private String title;

    public DateDTO(Date startDay, Date endDay, String memberId, String title) {
        this.startDay = startDay;
        this.endDay = endDay;
        this.memberId = memberId;
        this.title = title;
    }
}