package com.triplan.planner.community.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Reply {

    private long rno;
    private String rcontent;
    private long ref;
    private int rstep;
    private Date rwritetime;
    private String memberId;
    private long bno;
}
