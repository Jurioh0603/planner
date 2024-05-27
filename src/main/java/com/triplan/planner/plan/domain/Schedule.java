package com.triplan.planner.plan.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Schedule {

    private long scheduleNo;
    private Date startDay;
    private Date endDay;
    private String scheduleTitle;
    private String memberId;
}
