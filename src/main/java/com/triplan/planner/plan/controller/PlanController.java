package com.triplan.planner.plan.controller;

import com.triplan.planner.plan.domain.ScheduleImage;
import com.triplan.planner.plan.dto.*;
import com.triplan.planner.plan.file.FileStore;
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
    public String saveItem(@ModelAttribute("planImg") imageUploadForm imageUploadForm) throws IOException {
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

    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("KAKAO_API_KEY", KAKAO_API_KEY);
        return "plan/planForm";
    }

    @GetMapping("/modify")
    public String modify(@RequestParam("no") Long scheduleNo, Model model) {
        ScheduleList scheduleList = planService.getScheduleList(scheduleNo);

        model.addAttribute("KAKAO_API_KEY", KAKAO_API_KEY);
        model.addAttribute("scheduleList", scheduleList);

        return "plan/modifyForm";
    }

    @GetMapping("/addAttr")
    public String addAttr(@ModelAttribute("day") int day) {
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
