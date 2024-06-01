package com.triplan.planner.admin.controller;

import com.triplan.planner.admin.domain.Community;
import com.triplan.planner.admin.domain.CommunityPage;
import com.triplan.planner.admin.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/community/posts/paged")
    @ResponseBody
    public CommunityPage getPagedCommunityPosts(@RequestParam(name = "category", required = true) String category,
                                                @RequestParam(name = "page", defaultValue = "1") int page,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        List<Community> posts = communityService.getPagedPostsByCategory(category, page, size);
        int total = communityService.getCountByCategory(category);

        return new CommunityPage(total, page, size, posts);
    }

    @PostMapping("/community/delete")
    @ResponseBody
    public List<String> deletePosts(@RequestBody List<String> boardIdxArray, @RequestParam(name = "category") String category) {
        communityService.deletePosts(boardIdxArray, category);
        return boardIdxArray;
    }
}
