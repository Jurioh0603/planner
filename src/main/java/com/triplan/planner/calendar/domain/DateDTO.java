package com.triplan.planner.calendar.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DateDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDay;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDay;
    private String memberId;

    public DateDTO(Date startDay, Date endDay , String memberId) {
        this.startDay = startDay;
        this.endDay = endDay;
        this.memberId = memberId;

    }
}
