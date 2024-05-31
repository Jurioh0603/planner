package com.triplan.planner.mypage.controller;

import com.triplan.planner.file.FileStore;
import com.triplan.planner.file.UploadFile;
import com.triplan.planner.mypage.dto.Profile;
import com.triplan.planner.mypage.dto.ProfileForm;
import com.triplan.planner.mypage.dto.UpdateInfoForm;
import com.triplan.planner.mypage.service.MyPageService;
import jakarta.servlet.http.HttpSession;
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
    // 마이페이지 요청
    @GetMapping("/mypage/myPage")
    public String MyPageForm(Model model,
                             HttpSession session){
        String memberId = "id1";
        Profile profile = myPageService.getProfileList(memberId);
        session.setAttribute("memberId", memberId);
        System.out.println(profile);
        model.addAttribute("profile", profile);
        return "/mypage/myPage";
    }
    // 프로필 사진 업데이트 요청
    @PostMapping("/mypage/upload")
    public String saveProfile(HttpSession session,
                              @ModelAttribute ProfileForm form,
                              Model model) throws IOException {
        UploadFile uploadFile = fileStore.storeFile(form.getUploadFile());
        Profile profile = new Profile();
        profile.setMemberId((String) session.getAttribute("memberId"));
        if (form.getUploadFile().isEmpty()) {
            myPageService.updateProfile(profile);
        } else {
            //변경된 파일 저장
            profile.setMImg(uploadFile.getUploadFileName());
            profile.setMCopyImg(uploadFile.getStoreFileName());

            myPageService.updateProfile(profile);
            model.addAttribute("profile", profile);
        }
        return "redirect:/mypage/myPage";
    }
    // 파일 로컬 컴퓨터 지정한 경로에 저장
    @ResponseBody
    @GetMapping("/mypage/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }
    // 마이페이지 내 정보 보기 요청
    @GetMapping("/mypage/myInfo")
    public String myInfo(Model model){
        String memberId = "id1";
        Profile profile = myPageService.getProfileList(memberId);
        System.out.println(profile);
        model.addAttribute("profile", profile);
        return "/mypage/myInfo";
    }
    // 마이페이지 내 정보 수정 요청
    @PostMapping("/mypage/updateInfo")
    public String updateInfo(@ModelAttribute UpdateInfoForm updateInfoForm,
                             HttpSession session,
                             @RequestParam String password1,
                             @RequestParam String password2,
                             Model model) {
        Profile profile = new Profile();
        if (!password1.equals(password2) || password1.trim().isEmpty()) {
            return "/mypage/error";
        } else {
            //데이터베이스에 저장
            profile.setMemberId((String) session.getAttribute("memberId"));
            profile.setNickName(updateInfoForm.getNickName());
            profile.setPassword(updateInfoForm.getPassword1().replaceAll(" ","")); // 공백제거 후 저장
            System.out.println(profile);
            myPageService.updateInfo(profile);
            model.addAttribute(profile);
            return "redirect:/mypage/myPage";
        }
    }

    // Get매핑 시도시 에러 페이지로 리다이렉트
    @GetMapping("/mypage/updateInfo")
    public String handleSpecificPath() {
        return "redirect:/mypage/error";
    }

    // 즐겨찾기 목록 요청
    @GetMapping("/mypage/myFav")
    public String myFavList(){
        return "/mypage/myFav";
    }

}