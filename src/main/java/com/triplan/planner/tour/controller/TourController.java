package com.triplan.planner.tour.controller;

import com.triplan.planner.tour.dto.Api;
import com.triplan.planner.tour.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping("/api/tourList/save")
    public ResponseEntity<String> saveTourList(Api api) {

        tourService.saveTourList(api);
        return ResponseEntity.ok("Tourist attraction saved successfully!");
    }
}
