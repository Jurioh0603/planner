package com.triplan.planner.tour.controller;

import com.triplan.planner.tour.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping("/tour/saveAll")
    public ResponseEntity<String> saveAllTours() {
        try {
            int startContentId = 126182;
            int endContentId = 129700;

            for (int contentId = startContentId; contentId <= endContentId; contentId++) {
                tourService.saveTour(String.valueOf(contentId));
            }

            return ResponseEntity.ok("All tourist attractions saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }

    //하나씩 값을 넣을 때
    /*@GetMapping("/tour/save")
    public ResponseEntity<String> saveTour(@RequestParam String contentId){

        try {
            tourService.saveTour(contentId);
            return ResponseEntity.ok("Tourist attraction saved successfully!");
        } catch (URISyntaxException | JsonProcessingException e) {
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }

    }*/
}
