package com.triplan.planner.tlog.service;

import com.triplan.planner.plan.domain.Schedule;
import com.triplan.planner.tlog.domain.Tlog;
import com.triplan.planner.tlog.domain.TlogImage;
import com.triplan.planner.tlog.dto.TlogDetailInfo;
import com.triplan.planner.tlog.dto.TlogList;
import com.triplan.planner.tlog.repository.TlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TlogServiceImpl implements TlogService {

    private final TlogRepository tlogRepository;

    public List<Schedule> getScheduleList(String memberId) {
        return tlogRepository.getScheduleList(memberId);
    }

    public void writeTlog(Tlog tlog, List<TlogImage> tlogImageList) {
        tlogRepository.saveTlog(tlog, tlogImageList);
    }

    @Override
    public List<TlogList> getTlogList() {
        return tlogRepository.getTlogList();
    }

    @Override
    public TlogDetailInfo getTlogInfo(long tlogNo) {
        TlogDetailInfo tlogInfo = tlogRepository.getTlogInfo(tlogNo);

        List<Date> travelDay = new ArrayList<>();
        Date startDay = tlogInfo.getSchedule().getStartDay();
        Date endDay = tlogInfo.getSchedule().getEndDay();
        Date day = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDay);
        do {
            day = calendar.getTime();
            travelDay.add(day);
            calendar.add(Calendar.DATE, 1);
        } while(endDay.compareTo(day) > 0);
        tlogInfo.setTravelDay(travelDay);

        return tlogInfo;
    }
}
