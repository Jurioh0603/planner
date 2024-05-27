package com.triplan.planner.plan.repository;

import com.triplan.planner.plan.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PlanRepositoryImpl implements PlanRepository {

    private final PlanMapper planMapper;

    public PlanList findPlanList(String memberId) {
        int count = planMapper.getCount(memberId);
        List<Schedule> scheduleList = planMapper.getSchedules(memberId);

        long[] scheduleNoArray = new long[count];
        for(int i = 0; i < count; i++) {
            scheduleNoArray[i] = scheduleList.get(i).getScheduleNo();
        }

        List<PlaceList> placeLists = planMapper.getPlaces(scheduleNoArray);

        Map<Long, List<String>> placeList = new HashMap<>();
        for( PlaceList place : placeLists ) {
            if(!placeList.containsKey(place.getScheduleNo())) {
                List<String> placeByNo = new ArrayList<>();
                placeByNo.add(place.getPlaceName());
                placeList.put(place.getScheduleNo(), placeByNo);
            } else {
                placeList.get(place.getScheduleNo()).add(place.getPlaceName());
            }
        }

        List<imageUploadForm> imageUploadForms = planMapper.getImages(memberId);

        return new PlanList(count, scheduleList, placeList, imageUploadForms);
    }

    public ScheduleList findScheduleList(Long scheduleNo) {
        Schedule schedule = planMapper.getScheduleByNo(scheduleNo);
        List<DetailSchedule> detailScheduleList = planMapper.getDetailSchedules(scheduleNo);
        return new ScheduleList(schedule, detailScheduleList);
    }

    public void save(ScheduleImage scheduleImage) {
        ScheduleImage imageExist = planMapper.isImageExist(scheduleImage.getScheduleNo());
        if(imageExist != null) {
            planMapper.deleteImage(scheduleImage.getScheduleNo());
        }
        planMapper.save(scheduleImage);
    }

    public void deleteScheduleByNo(Long scheduleNo) {
        planMapper.deleteScheduleByNo(scheduleNo);
    }
}
