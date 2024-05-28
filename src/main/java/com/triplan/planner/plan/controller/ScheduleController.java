package com.triplan.planner.plan.controller;

import com.triplan.planner.plan.domain.DetailSchedule;
import com.triplan.planner.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final PlanService planService;

    @PostMapping("/plan/save")
    @ResponseBody
    public List<Map<String, String>> saveSchedule(@RequestBody List<Map<String, String>> scheduleArray) {
        List<DetailSchedule> detailScheduleList = new ArrayList<>();
        for (Map<String, String> schedule : scheduleArray) {
            DetailSchedule detailSchedule = new DetailSchedule(0L,
                    Long.parseLong(schedule.get("scheduleNo")),
                    Integer.parseInt(schedule.get("detailDay")),
                    Integer.parseInt(schedule.get("placeProc")),
                    schedule.get("placeName"),
                    Double.parseDouble(schedule.get("placeLatitude")),
                    Double.parseDouble(schedule.get("placeLongitude")),
                    schedule.get("placeMemo"));
            detailScheduleList.add(detailSchedule);
        }

        planService.saveSchedule(detailScheduleList);

        return scheduleArray;
    }
}
