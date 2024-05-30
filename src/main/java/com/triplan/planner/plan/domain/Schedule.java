package com.triplan.planner.plan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Schedule {

    private long scheduleNo;
    private Date startDay;
    private Date endDay;
    private String scheduleTitle;
    private String memberId;
}
