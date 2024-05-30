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
    public List<TlogList> getTlogList() {
        return tlogMapper.getTlogs();
    }

    @Override
    public TlogDetailInfo getTlogInfo(long tlogNo) {
        Tlog tlog = tlogMapper.getTlogByNo(tlogNo);
        List<TlogImage> tlogImageList = tlogMapper.getTlogImageByNo(tlogNo);
        Schedule schedule = planMapper.getScheduleByNo(tlog.getScheduleNo());
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
}
