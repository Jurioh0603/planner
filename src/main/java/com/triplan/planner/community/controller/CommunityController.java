package com.triplan.planner.community.controller;

import com.triplan.planner.community.domain.Community;
import com.triplan.planner.community.dto.CommunityDetail;
import com.triplan.planner.community.dto.CommunityList;
import com.triplan.planner.community.dto.CommunityPage;
import com.triplan.planner.community.service.CommunityService;
import com.triplan.planner.file.FileStore;
import com.triplan.planner.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;
    private final FileStore fileStore;

    private static Map<String, String> weather = Map.of("S", "seoul", "GG", "suwon", "GW", "chuncheon",
                                                        "CB", "cheongju", "CN","hongseong", "JB", "jeonju",
                                                        "JN", "muan", "GB", "andong", "GN", "changwon", "JJ", "jeju");

    @GetMapping("/list")
    public String list(@ModelAttribute("local") String local, @ModelAttribute("page") String page,
                       @ModelAttribute("search") String search, Model model) {
        //local 파라미터 없이 접근 시 기본값 S(서울)
        if(local.isEmpty())
            local = "S";
        //page 파라미터 없이 접근 시 기본값 1(1페이지)
        if(page.isEmpty()) {
            page = "1";
        }
        CommunityList communityList = communityService.getCommunityList(local, Integer.parseInt(page), search);

        //페이지네이션
        int total = communityService.getCount(local, search);
        int size = 10;

        CommunityPage communityPage = new CommunityPage(total, Integer.parseInt(page), size, communityList);
        model.addAttribute("communityPage", communityPage);
        model.addAttribute("local", local);
        model.addAttribute("weather", weather.get(local));
        return "community/commuList";
    }

    @GetMapping("/write")
    public String write(@RequestParam(defaultValue = "S") String local, Model model) {
        model.addAttribute("local", local);
        return "community/writeForm";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Community community, @RequestParam("local") String local, @SessionAttribute("loginMemberInfo") UserDto loginInfo) {
        String memberId = loginInfo.getMemberId();
        community.setMemberId(memberId);
        long bno = communityService.write(community, local);
        return "redirect:/community/detail?local=" + local + "&no=" + bno;
    }

    @GetMapping("/modify")
    public String modify(@RequestParam("local") String local, @RequestParam("no") long bno, Model model, HttpSession session) {
        Community community = communityService.getCommunity(local, bno);

        UserDto loginInfo = (UserDto) session.getAttribute("loginMemberInfo");
        //로그인하지 않았거나 작성자가 아닌 경우 수정 불가 -> 에러 페이지 이동
        if(loginInfo == null || !loginInfo.getMemberId().equals(community.getMemberId())) {
            return "redirect:/error/forbidden";
        }

        model.addAttribute("community", community);
        model.addAttribute("local", local);
        return "community/modifyForm";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Community community, @RequestParam("local") String local) {
        communityService.modify(community, local);
        return "redirect:/community/detail?local=" + local + "&no=" + community.getBno();
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("local") String local, @RequestParam("no") long bno, Model model) {
        CommunityDetail communityDetail = communityService.getCommunityDetail(local, bno);
        model.addAttribute("communityDetail", communityDetail);
        model.addAttribute("local", local);
        return "community/commuDetail";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("local") String local, @RequestParam("no") long bno, Model model) {
        communityService.deleteCommunity(local, bno);
        return "redirect:/community/list?local=" + local;
    }
}
