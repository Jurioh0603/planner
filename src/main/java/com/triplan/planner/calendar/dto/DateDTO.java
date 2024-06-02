package com.triplan.planner.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DateDTO {

    private Date startDay;
    private Date endDay;
    private String memberId;
    private String title;
}