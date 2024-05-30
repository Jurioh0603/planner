package com.triplan.planner.mypage.controller;

import com.triplan.planner.file.FileStore;
import com.triplan.planner.file.UploadFile;
import com.triplan.planner.mypage.dto.Profile;
import com.triplan.planner.mypage.dto.ProfileForm;
import com.triplan.planner.mypage.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPage {

    private final MyPageService myPageService;
    private final FileStore fileStore;

    @GetMapping("/mypage/myPage")
    public String MyPageForm(Model model){
        String memberId = "id1";
        Profile profile = myPageService.getProfileList(memberId);
        System.out.println(profile);
        model.addAttribute("memberId", memberId);
        model.addAttribute("profile", profile);
        return "/mypage/myPage";
    }

    @PostMapping("/mypage/upload")
    public String saveProfile(@ModelAttribute ProfileForm form,
                              Model model) throws IOException {
        UploadFile uploadFile = fileStore.storeFile(form.getUploadFile());
        //데이터베이스에 저장
        Profile profile = new Profile();
        profile.setMemberId("id1");
        profile.setMImg(uploadFile.getUploadFileName());
        profile.setMCopyImg(uploadFile.getStoreFileName());

        myPageService.updateProfile(profile);
        System.out.println(profile.getMemberId());
        System.out.println(profile.getMImg());
        System.out.println(profile.getMCopyImg());
        model.addAttribute("profile", profile);
        return "redirect:/mypage/myPage";
    }

    @ResponseBody
    @GetMapping("/mypage/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }
}