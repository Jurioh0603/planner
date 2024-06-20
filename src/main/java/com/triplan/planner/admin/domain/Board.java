package com.triplan.planner.admin.domain;

import lombok.Data;

@Data
public class Board {
    private int bno;
    private String title;
    private String memberId;
    private String writeTime;
}
