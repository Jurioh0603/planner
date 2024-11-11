package com.triplan.planner.community.domain;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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

    public String getWriteTimeFormatted() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate writeDate = rwritetime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        //하루 이상 전 -> 날짜
        if(writeDate.isEqual(today)) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            return format.format(rwritetime);
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
            return format.format(rwritetime);
        }
    }
}
