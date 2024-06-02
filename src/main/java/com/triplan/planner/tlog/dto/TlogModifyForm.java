package com.triplan.planner.tlog.dto;

import lombok.Data;

@Data
public class TlogModifyForm {

    private String title;
    private String content;
    private String imageList;
    private String scheduleTitle;
    private long scheduleNo;
}
