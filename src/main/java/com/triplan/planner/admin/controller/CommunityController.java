package com.triplan.planner.admin.controller;

import com.triplan.planner.admin.domain.Community;
import com.triplan.planner.admin.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/community")
    public String getCommunityPage(@RequestParam(name = "category", required = false, defaultValue = "S_COMMUNITY") String category,
                                   @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                   @RequestParam(name = "searchType", required = false, defaultValue = "title") String searchType,
                                   @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
                                   Model model) {
        model.addAttribute("selectedCategory", category);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchQuery", searchQuery);
        return "admin/boardPage";
    }

    @GetMapping("/community/posts")
    @ResponseBody
    public List<Community> getCommunityPosts(@RequestParam(name = "category", required = true) String category) {
        return communityService.getPostsByCategory(category);
    }

    @GetMapping("/community/posts/paged")
    @ResponseBody
    public Map<String, Object> getPagedPostsByCategory(
            @RequestParam String category,
            @RequestParam int page,
            @RequestParam(name = "searchType", required = false, defaultValue = "title") String searchType,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery) {

        int pageSize = 10;
        int startRow = (page - 1) * pageSize;
        List<Community> posts = communityService.getPagedPostsByCategory(category, searchType, searchQuery, startRow, pageSize);
        int totalPosts = communityService.getCountByCategory(category, searchType, searchQuery);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

        Map<String, Object> result = new HashMap<>();
        result.put("communityList", posts);
        result.put("totalPages", totalPages);
        return result;
    }

    @PostMapping("/community/delete")
    @ResponseBody
    public Map<String, String> deletePosts(@RequestBody Map<String, Object> params) {
        List<Integer> boardIdxArray = (List<Integer>) params.get("boardIdxArray");
        String category = (String) params.get("category");
        communityService.deletePosts(boardIdxArray, category);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Posts deleted successfully");
        return response;
    }

    @GetMapping("/community/search")
    @ResponseBody
    public List<Community> searchPosts(@RequestParam String category, @RequestParam String searchType, @RequestParam String searchQuery) {
        return communityService.searchPosts(category, searchType, searchQuery);
    }
}
