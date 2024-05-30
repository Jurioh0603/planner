package com.triplan.planner.plan.repository;

import com.triplan.planner.plan.domain.DetailSchedule;
import com.triplan.planner.plan.domain.Schedule;
import com.triplan.planner.plan.domain.ScheduleImage;
import com.triplan.planner.plan.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlanMapper {

    public int getCount(String memberId);
    public List<Schedule> getSchedules(String memberId);
    public List<Schedule> getPostSchedules(String memberId);
    public List<Schedule> getPreSchedules(String memberId);
    public List<PlaceList> getPlaces(long[] scheduleNoArray);
    public List<imageUploadForm> getImages(String memberId);

    public Schedule getScheduleByNo(Long scheduleNo);
    public List<DetailSchedule> getDetailSchedules(Long scheduleNo);

    public ScheduleImage isImageExist(Long scheduleNo);
    public void deleteImage(Long scheduleNo);
    public void save(ScheduleImage scheduleImage);

    public void deleteScheduleByNo(Long scheduleNo);

    public void deleteDetailScheduleByScheduleNo(long scheduleNo);
    public void insertDetailSchedules(List<DetailSchedule> detailScheduleList);

    public void updateScheduleTitleByNo(@Param("scheduleNo") Long scheduleNo, @Param("title") String title);
}
