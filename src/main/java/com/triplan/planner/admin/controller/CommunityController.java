package com.triplan.planner.admin.controller;

import com.triplan.planner.admin.domain.Community;
import com.triplan.planner.admin.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/community")
    public String getCommunityPage(@RequestParam(name = "category", required = false, defaultValue = "S_COMMUNITY") String category, Model model) {
        List<Community> posts = communityService.getPostsByCategory(category);
        model.addAttribute("posts", posts);
        model.addAttribute("selectedCategory", category);
        return "admin/boardPage";
    }

    @GetMapping("/community/posts")
    @ResponseBody
    public List<Community> getCommunityPosts(@RequestParam(name = "category", required = true) String category) {
        return communityService.getPostsByCategory(category);
    }
}
