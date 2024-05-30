package com.triplan.planner.tlog.service;

import com.triplan.planner.plan.domain.Schedule;
import com.triplan.planner.tlog.domain.Tlog;
import com.triplan.planner.tlog.domain.TlogImage;
import com.triplan.planner.tlog.dto.TlogDetailInfo;
import com.triplan.planner.tlog.dto.TlogList;
import com.triplan.planner.tlog.dto.TlogModifyForm;

import java.util.List;

public interface TlogService {

    public List<Schedule> getScheduleList(String memberId);

    public void writeTlog(Tlog tlog, List<TlogImage> tlogImageList);

    public List<TlogList> getTlogList();

    public TlogDetailInfo getTlogInfo(long tlogNo);

    public void delete(long tlogNo);

    public TlogModifyForm getTlogModifyInfo(long tlogNo);

    public void modifyTlog(Tlog tlog, List<TlogImage> tlogImageList);
}
