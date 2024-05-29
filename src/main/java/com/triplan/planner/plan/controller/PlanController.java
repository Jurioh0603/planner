package com.triplan.planner.plan.controller;

import com.triplan.planner.file.FileStore;
import com.triplan.planner.file.UploadFile;
import com.triplan.planner.plan.domain.DetailSchedule;
import com.triplan.planner.plan.domain.ScheduleImage;
import com.triplan.planner.plan.dto.PlanList;
import com.triplan.planner.plan.dto.ScheduleList;
import com.triplan.planner.plan.dto.imageUploadForm;
import com.triplan.planner.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/plan")
public class PlanController {

    private final PlanService planService;
    private final FileStore fileStore;

    @Value("${KAKAO_API_KEY}")
    private String KAKAO_API_KEY;

    @GetMapping("/list")
    public String list(Model model) {
        String memberId = "id1"; //나중에 세션에서 로그인한 id를 가져옴
        PlanList planList = planService.getPlanList(memberId);

        model.addAttribute("planList", planList);

        return "plan/myPlanList";
    }

    @PostMapping("/upload")
    public String saveItem(@ModelAttribute imageUploadForm imageUploadForm) throws IOException {
        UploadFile uploadFile = fileStore.storeFile(imageUploadForm.getUploadFile());

        ScheduleImage scheduleImage = new ScheduleImage();
        scheduleImage.setScheduleNo(imageUploadForm.getScheduleNo());
        scheduleImage.setUploadName(uploadFile.getUploadFileName());
        scheduleImage.setStoreName(uploadFile.getStoreFileName());

        planService.save(scheduleImage);

        return "redirect:/plan/list";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @GetMapping("/delete")
    public String deletePlan(@RequestParam("no") Long scheduleNo) {
        planService.deletePlan(scheduleNo);
        return "redirect:/plan/list";
    }

    @PostMapping("/modifyTitle")
    public String modifyTitle(@RequestParam("no") Long scheduleNo, @RequestParam("editTitle") String title) {
        planService.modifyTitle(scheduleNo, title);
        return "redirect:/plan/list";
    }

    @GetMapping("/write")
    public String write(@RequestParam("no") Long scheduleNo, Model model) {
        ScheduleList scheduleList = planService.getScheduleList(scheduleNo);

        model.addAttribute("KAKAO_API_KEY", KAKAO_API_KEY);
        model.addAttribute("scheduleList", scheduleList);
        model.addAttribute("scheduleNo", scheduleNo);

        return "plan/planForm";
    }

    @PostMapping("/save")
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

    @GetMapping("/addAttr")
    public String addAttr(@ModelAttribute("day") int day, @ModelAttribute("keyword") String keyword, Model model) {
        model.addAttribute("KAKAO_API_KEY", KAKAO_API_KEY);
        return "plan/addAttraction";
    }

    @GetMapping("/addMyPlace")
    public String addMyPlace(@ModelAttribute("day") int day, Model model) {
        model.addAttribute("KAKAO_API_KEY", KAKAO_API_KEY);
        return "plan/addMyPlace";
    }

    @GetMapping("/addOnMap")
    public String addOnMap(@ModelAttribute("day") int day, Model model) {
        model.addAttribute("KAKAO_API_KEY", KAKAO_API_KEY);
        return "plan/addOnMap";
    }
}
