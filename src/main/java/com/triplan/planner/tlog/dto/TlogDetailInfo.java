package com.triplan.planner.tlog.dto;

import com.triplan.planner.plan.domain.DetailSchedule;
import com.triplan.planner.plan.domain.Schedule;
import com.triplan.planner.tlog.domain.Tlog;
import com.triplan.planner.tlog.domain.TlogImage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class TlogDetailInfo {

    private Tlog tlog;
    private List<TlogImage> tlogImageList;
    private Schedule schedule;
    private List<DetailSchedule> detailScheduleList;
    private List<Date> travelDay;
}
