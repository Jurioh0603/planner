package com.triplan.planner.community.domain;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Data
public class Community {

    private long bno;
    private String title;
    private String content;
    private Date writeTime;
    private String memberId;

    public String getWriteTimeFormatted() {
        LocalDate today = LocalDate.now();
        LocalDate writeDate = writeTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        //오늘 작성한 글은 시간, 그 외는 날짜 출력
        if(writeDate.isEqual(today)) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            return format.format(writeTime);
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
            return format.format(writeTime);
        }
    }
}
