package com.triplan.planner.plan.domain;

import lombok.Data;

@Data
public class DetailSchedule {

    private long detailscheduleNo;
    private long scheduleNo;
    private int detailDay;
    private int placeProc;
    private String placeName;
    private double placeLatitude;
    private double placeLongitude;
    private String placeMemo;
}
