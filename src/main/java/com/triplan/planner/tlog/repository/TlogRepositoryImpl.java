package com.triplan.planner.tlog.repository;

import com.triplan.planner.plan.domain.DetailSchedule;
import com.triplan.planner.plan.domain.Schedule;
import com.triplan.planner.plan.repository.PlanMapper;
import com.triplan.planner.tlog.domain.Tlog;
import com.triplan.planner.tlog.domain.TlogImage;
import com.triplan.planner.tlog.dto.TlogDetailInfo;
import com.triplan.planner.tlog.dto.TlogList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TlogRepositoryImpl implements TlogRepository {

    private final TlogMapper tlogMapper;
    private final PlanMapper planMapper;

    @Override
    public List<Schedule> getScheduleList(String memberId, String search) {
        return planMapper.getSchedules(memberId, search);
    }

    @Override
    public void saveTlog(Tlog tlog, List<TlogImage> tlogImageList) {
        tlogMapper.saveTlog(tlog);

        Long tlogNo = tlog.getTlogNo();
        for(int i = 0; i < tlogImageList.size(); i++) {
            tlogImageList.get(i).setTlogNo(tlogNo);
        }

        tlogMapper.saveTlogImage(tlogImageList);
    }

    @Override
    public List<TlogList> getTlogList(int startRow, int size) {
        return tlogMapper.getTlogs(startRow, size);
    }

    @Override
    public int getCount() {
        return tlogMapper.getCount();
    }

    @Override
    public TlogDetailInfo getTlogInfo(long tlogNo) {
        //tlog
        Tlog tlog = tlogMapper.getTlogByNo(tlogNo);
        //tlogImage
        List<TlogImage> tlogImageList = tlogMapper.getTlogImageByNo(tlogNo);
        //schedule
        Schedule schedule = planMapper.getScheduleByNo(tlog.getScheduleNo());
        //detailSchedule
        List<DetailSchedule> detailScheduleList = planMapper.getDetailSchedules(tlog.getScheduleNo());

        return new TlogDetailInfo(tlog, tlogImageList, schedule, detailScheduleList, null);
    }

    @Override
    public void deleteTlogByNo(long tlogNo) {
        tlogMapper.deleteTlogByNo(tlogNo);
    }

    @Override
    public void updateTlog(Tlog tlog, List<TlogImage> tlogImageList) {
        tlogMapper.updateTlog(tlog);

        if (!tlogImageList.isEmpty()) {
            tlogMapper.deleteTlogImageByNo(tlog.getTlogNo());
            tlogMapper.saveTlogImage(tlogImageList);
        }
    }

    @Override
    public void saveSchedule(long scheduleNo, String memberId) {
        //schedule 가져오기
        Schedule yourSchedule = planMapper.getScheduleByNo(scheduleNo);

        //schedule 가공하기
        long diffDaysLong = (yourSchedule.getEndDay().getTime() - yourSchedule.getStartDay().getTime()) / 1000 / (24 * 60 * 60);
        int diffDaysInt = Long.valueOf(diffDaysLong).intValue();

        Date startDay = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDay);
        cal.add(Calendar.DATE, diffDaysInt);

        Date endDay = cal.getTime();

        //schedule 저장하기
        Schedule mySchedule = new Schedule(0, startDay, endDay, "여행기에서 저장한 일정입니다.", memberId);
        planMapper.insertSchedule(mySchedule);

        long lastScheduleNo = planMapper.getLastScheduleNo();

        //detailSchedule 가져오기
        List<DetailSchedule> detailScheduleList = planMapper.getDetailSchedules(scheduleNo);

        if(!detailScheduleList.isEmpty()) {
            //detailSchedule 가공하기
            for(int i = 0; i < detailScheduleList.size(); i++) {
                detailScheduleList.get(i).setScheduleNo(lastScheduleNo);
            }

            //detailSchedule 저장하기
            planMapper.insertDetailSchedules(detailScheduleList);
        }
    }

    @Override
    public boolean isFav(String memberId, long tlogNo) {
        List<Long> favList = tlogMapper.getFavList(memberId);
        if(favList.contains(tlogNo))
            return true;
        else
            return false;
    }

    @Override
    public void saveFav(long tlogNo, String memberId) {
        tlogMapper.saveFav(tlogNo, memberId);
    }

    @Override
    public void deleteFav(long tlogNo, String memberId) {
        tlogMapper.deleteFav(tlogNo, memberId);
    }
}
