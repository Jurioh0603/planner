package com.triplan.planner.tlog.repository;

import com.triplan.planner.plan.domain.Schedule;
import com.triplan.planner.tlog.domain.Tlog;
import com.triplan.planner.tlog.domain.TlogImage;
import com.triplan.planner.tlog.dto.TlogDetailInfo;
import com.triplan.planner.tlog.dto.TlogList;

import java.util.List;

public interface TlogRepository {

    public List<Schedule> getScheduleList(String memberId, String search);

    public void saveTlog(Tlog tlog, List<TlogImage> tlogImageList);

    public List<TlogList> getTlogList(int startRow, int size);
    public int getCount();

    public TlogDetailInfo getTlogInfo(long tlogNo);

    public void deleteTlogByNo(long tlogNo);

    public void updateTlog(Tlog tlog, List<TlogImage> tlogImageList);

    public void saveSchedule(long scheduleNo, String memberId);

    public boolean isFav(String memberId, long tlogNo);
    public void saveFav(long tlogNo, String memberId);
    public void deleteFav(long tlogNo, String memberId);
}
