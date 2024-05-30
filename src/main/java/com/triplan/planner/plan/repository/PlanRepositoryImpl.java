package com.triplan.planner.plan.repository;

import com.triplan.planner.plan.domain.DetailSchedule;
import com.triplan.planner.plan.domain.Schedule;
import com.triplan.planner.plan.domain.ScheduleImage;
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
        //일정 개수
        int count = planMapper.getCount(memberId);
        //일정이 없다면
        if(count == 0)
            return new PlanList(0, null, null, null);
        //schedule 테이블에서 데이터 가져오기(당일, 미래, 과거 순)
        List<Schedule> scheduleList = null;
        List<Schedule> postScheduleList = planMapper.getPostSchedules(memberId);
        scheduleList = postScheduleList;
        List<Schedule> preScheduleList = planMapper.getPreSchedules(memberId);
        scheduleList.addAll(preScheduleList);

        //내가 작성한 일정 번호를 저장하는 list
        long[] scheduleNoArray = new long[count];
        for(int i = 0; i < count; i++) {
            scheduleNoArray[i] = scheduleList.get(i).getScheduleNo();
        }

        //내가 작성한 일정에 저장된 장소들의 list
        List<PlaceList> placeLists = planMapper.getPlaces(scheduleNoArray);

        //몇 번 글에 저장된 장소인지 구분하여 저장하기 위한 map
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

        //이미지 목록
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

    @Override
    public void insertDetailSchedules(List<DetailSchedule> detailScheduleList) {
        planMapper.deleteDetailScheduleByScheduleNo(detailScheduleList.get(0).getScheduleNo());
        planMapper.insertDetailSchedules(detailScheduleList);
    }

    @Override
    public void updateScheduleTitleByNo(Long scheduleNo, String title) {
        planMapper.updateScheduleTitleByNo(scheduleNo, title);
    }
}
