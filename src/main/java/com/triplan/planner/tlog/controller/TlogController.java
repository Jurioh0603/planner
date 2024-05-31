package com.triplan.planner.tlog.controller;

import com.triplan.planner.file.FileStore;
import com.triplan.planner.file.UploadFile;
import com.triplan.planner.plan.domain.Schedule;
import com.triplan.planner.tlog.domain.Tlog;
import com.triplan.planner.tlog.domain.TlogImage;
import com.triplan.planner.tlog.dto.*;
import com.triplan.planner.tlog.service.TlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/tlog")
public class TlogController {

    private final TlogService tlogService;
    private final FileStore fileStore;

    @Value("${KAKAO_API_KEY}")
    String KAKAO_API_KEY;

    @GetMapping("/list")
    public String list(@ModelAttribute("page") String page, Model model) {
        if(page.isEmpty()) {
            page = "1";
        }
        List<TlogList> tlogList = tlogService.getTlogList(Integer.parseInt(page));

        int total = tlogService.getCount();
        int size = 6;

        TlogPage tlogPage = new TlogPage(total, Integer.parseInt(page), size, tlogList);
        model.addAttribute("tlogPage", tlogPage);
        return "tlog/tlogList";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @GetMapping("/write")
    public String write(Model model) {
        return "tlog/tlogWriteForm";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute TlogWriteForm form) throws IOException {
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getFile());

        String memberId = "id1";
        Tlog tlog = new Tlog(0L, form.getTitle(), form.getContent(), null, memberId, form.getScheduleNo());
        List<TlogImage> tlogImageList = new ArrayList<>();
        for(int i = 0; i < storeImageFiles.size(); i++) {
            TlogImage tlogImage = new TlogImage();
            tlogImage.setUploadName(storeImageFiles.get(i).getUploadFileName());
            tlogImage.setStoreName(storeImageFiles.get(i).getStoreFileName());
            tlogImageList.add(tlogImage);
        }

        tlogService.writeTlog(tlog, tlogImageList);

        return "redirect:/tlog/list";
    }

    @GetMapping("/select")
    public String select(@ModelAttribute("search") String search, Model model) {
        String memberId = "id1";
        List<Schedule> scheduleList = tlogService.getScheduleList(memberId, search);
        model.addAttribute("scheduleList", scheduleList);
        return "tlog/addPlan";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("no") long tlogNo, Model model) {
        String memberId = "id1";
        TlogDetailInfo tlogInfo = tlogService.getTlogInfo(tlogNo);
        boolean isFav = tlogService.isFav(memberId, tlogNo);
        model.addAttribute("KAKAO_API_KEY", KAKAO_API_KEY);
        model.addAttribute("tlogInfo", tlogInfo);
        model.addAttribute("isFav", isFav);
        return "tlog/tlogDetail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("no") long tlogNo) {
        tlogService.delete(tlogNo);
        return "redirect:/tlog/list";
    }

    @GetMapping("/modify")
    public String modify(@RequestParam("no") long tlogNo, Model model) {
        TlogModifyForm tlogModifyForm = tlogService.getTlogModifyInfo(tlogNo);

        model.addAttribute("tlogModifyForm", tlogModifyForm);
        model.addAttribute("no", tlogNo);
        return "tlog/tlogModifyForm";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute TlogWriteForm form, @RequestParam("no") long no) throws IOException {
        List<UploadFile> storeImageFiles = null;
        if(!form.getFile().get(0).getOriginalFilename().isBlank()) {
            storeImageFiles = fileStore.storeFiles(form.getFile());
        }

        String memberId = "id1";
        Tlog tlog = new Tlog(no, form.getTitle(), form.getContent(), null, memberId, form.getScheduleNo());
        List<TlogImage> tlogImageList = new ArrayList<>();
        if(storeImageFiles != null) {
            for (int i = 0; i < storeImageFiles.size(); i++) {
                TlogImage tlogImage = new TlogImage();
                tlogImage.setTlogNo(no);
                tlogImage.setUploadName(storeImageFiles.get(i).getUploadFileName());
                tlogImage.setStoreName(storeImageFiles.get(i).getStoreFileName());
                tlogImageList.add(tlogImage);
            }
        }

        tlogService.modifyTlog(tlog, tlogImageList);

        return "redirect:/tlog/detail?no=" + no;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveSchedule(@RequestBody Map<String, Object> jsonObj) {
        String scheduleNo = (String) jsonObj.get("scheduleNo");

        String memberId = "id1";
        tlogService.saveSchedule(Long.parseLong(scheduleNo), memberId);

        return ResponseEntity.ok("{\"message\": \"일정 저장 성공!\"}");
    }

    @GetMapping("/fav")
    public String fav(@RequestParam("tlogNo") long tlogNo) {
        String memberId = "id1";
        tlogService.favoriteTlog(tlogNo, memberId);
        return "redirect:/tlog/detail?no=" + tlogNo;
    }

    @GetMapping("/notFav")
    public String notFav(@RequestParam("tlogNo") long tlogNo) {
        String memberId = "id1";
        tlogService.notFavoriteTlog(tlogNo, memberId);
        return "redirect:/tlog/detail?no=" + tlogNo;
    }
}
