package com.triplan.planner.tlog.service;

import com.triplan.planner.plan.domain.Schedule;
import com.triplan.planner.tlog.domain.Tlog;
import com.triplan.planner.tlog.domain.TlogImage;
import com.triplan.planner.tlog.dto.TlogDetailInfo;
import com.triplan.planner.tlog.dto.TlogList;
import com.triplan.planner.tlog.dto.TlogModifyForm;

import java.util.List;

public interface TlogService {

    public List<Schedule> getScheduleList(String memberId, String search);

    public void writeTlog(Tlog tlog, List<TlogImage> tlogImageList);

    public List<TlogList> getTlogList(int page);
    public int getCount();

    public TlogDetailInfo getTlogInfo(long tlogNo);

    public void delete(long tlogNo);

    public TlogModifyForm getTlogModifyInfo(long tlogNo);

    public void modifyTlog(Tlog tlog, List<TlogImage> tlogImageList);

    public void saveSchedule(long scheduleNo, String memberId);

    public boolean isFav(String memberId, long tlogNo);
    public void favoriteTlog(long tlogNo, String memberId);
    public void notFavoriteTlog(long tlogNo, String memberId);
}
