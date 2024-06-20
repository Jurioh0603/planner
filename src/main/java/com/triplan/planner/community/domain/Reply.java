package com.triplan.planner.community.domain;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = new Date(cal.getTimeInMillis());

        //하루 이상 전 -> 날짜
        if(date.compareTo(rwritetime) < 0) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            return format.format(rwritetime);
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
            return format.format(rwritetime);
        }
    }
}
