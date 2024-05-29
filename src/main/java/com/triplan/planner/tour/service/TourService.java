package com.triplan.planner.tour.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.triplan.planner.tour.controller.ApiExplorer;
import com.triplan.planner.tour.dto.Api;
import com.triplan.planner.tour.repository.TourMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Service
public class TourService {

    @Autowired
    private TourMapper tourMapper;

    @Autowired
    private ApiExplorer apiExplorer;

    public void saveTour(String contentId) throws URISyntaxException, JsonProcessingException {
        Api apiData = apiExplorer.getData(contentId);
        tourMapper.insertTourList(apiData);
    }
}
