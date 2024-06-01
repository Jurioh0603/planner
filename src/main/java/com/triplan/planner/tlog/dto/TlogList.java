package com.triplan.planner.tlog.dto;

import lombok.Data;

@Data
public class TlogList {

    private long tlogNo;
    private String tlogTitle;
    private String tlogContent;
    private String memberId;
    private String storeName;
    private Profile writerProfile;
}
