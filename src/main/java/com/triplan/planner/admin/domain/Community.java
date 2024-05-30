package com.triplan.planner.admin.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Community {
    private int bno;
    private String title;
    private String content;
    private Date writeTime;
    private String memberId;
}
