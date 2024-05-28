package com.triplan.planner.tlog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Tlog {

    private Long tlogNo;
    private String tlogTitle;
    private String tlogContent;
    private Date writeTime;
    private String memberId;
    private Long scheduleNo;
}
