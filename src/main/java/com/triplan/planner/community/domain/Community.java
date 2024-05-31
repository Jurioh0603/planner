package com.triplan.planner.community.domain;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
public class Community {

    private long bno;
    private String title;
    private String content;
    private Date writeTime;
    private String memberId;

    public String getWriteTimeFormatted() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = new Date(cal.getTimeInMillis());

        //하루 이상 전 -> 날짜
        if(date.compareTo(writeTime) < 0) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            return format.format(writeTime);
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(writeTime);
        }
    }
}
