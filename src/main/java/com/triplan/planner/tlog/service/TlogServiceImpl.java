package com.triplan.planner.tlog.service;

import com.triplan.planner.plan.domain.Schedule;
import com.triplan.planner.tlog.domain.Tlog;
import com.triplan.planner.tlog.domain.TlogImage;
import com.triplan.planner.tlog.dto.TlogDetailInfo;
import com.triplan.planner.tlog.dto.TlogList;
import com.triplan.planner.tlog.dto.TlogModifyForm;
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

    @Override
    public List<Schedule> getScheduleList(String memberId, String search) {
        return tlogRepository.getScheduleList(memberId, search);
    }

    public void writeTlog(Tlog tlog, List<TlogImage> tlogImageList) {
        tlogRepository.saveTlog(tlog, tlogImageList);
    }

    @Override
    public List<TlogList> getTlogList(int page) {
        int size = 6;
        return tlogRepository.getTlogList((page - 1) * size, size);
    }

    @Override
    public int getCount() {
        return tlogRepository.getCount();
    }

    @Override
    public TlogDetailInfo getTlogInfo(long tlogNo) {
        TlogDetailInfo tlogInfo = tlogRepository.getTlogInfo(tlogNo);
        extracted(tlogInfo);
        return tlogInfo;
    }

    @Override
    public void delete(long tlogNo) {
        tlogRepository.deleteTlogByNo(tlogNo);
    }

    @Override
    public TlogModifyForm getTlogModifyInfo(long tlogNo) {
        TlogDetailInfo tlogInfo = tlogRepository.getTlogInfo(tlogNo);
        extracted(tlogInfo);
        return getTlogModifyForm(tlogInfo);
    }

    @Override
    public void modifyTlog(Tlog tlog, List<TlogImage> tlogImageList) {
        tlogRepository.updateTlog(tlog, tlogImageList);
    }

    @Override
    public void saveSchedule(long scheduleNo, String memberId) {
        tlogRepository.saveSchedule(scheduleNo, memberId);
    }

    @Override
    public boolean isFav(String memberId, long tlogNo) {
        return tlogRepository.isFav(memberId, tlogNo);
    }

    @Override
    public void favoriteTlog(long tlogNo, String memberId) {
        tlogRepository.saveFav(tlogNo, memberId);
    }

    @Override
    public void notFavoriteTlog(long tlogNo, String memberId) {
        tlogRepository.deleteFav(tlogNo, memberId);
    }

    private static void extracted(TlogDetailInfo tlogInfo) {
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
    }

    private static TlogModifyForm getTlogModifyForm(TlogDetailInfo tlogInfo) {
        TlogModifyForm tlogModifyForm = new TlogModifyForm();
        tlogModifyForm.setTitle(tlogInfo.getTlog().getTlogTitle());
        tlogModifyForm.setContent(tlogInfo.getTlog().getTlogContent());
        tlogModifyForm.setScheduleTitle(tlogInfo.getSchedule().getScheduleTitle());
        String imageList = "";
        for(int i = 0; i < tlogInfo.getTlogImageList().size(); i++) {
            imageList += tlogInfo.getTlogImageList().get(i).getUploadName() + ", ";
        }
        imageList = imageList.substring(0, imageList.length() - 2);
        tlogModifyForm.setImageList(imageList);
        tlogModifyForm.setScheduleNo(tlogInfo.getTlog().getScheduleNo());
        tlogModifyForm.setMemberId(tlogInfo.getTlog().getMemberId());
        return tlogModifyForm;
    }
}
