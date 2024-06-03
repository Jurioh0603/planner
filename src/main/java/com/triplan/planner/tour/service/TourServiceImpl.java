package com.triplan.planner.tour.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.triplan.planner.tour.controller.ApiExplorer;
import com.triplan.planner.tour.dto.Api;
import com.triplan.planner.tour.dto.Attraction;
import com.triplan.planner.tour.repository.TourMapper;
import com.triplan.planner.tour.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService{

    private final TourMapper tourMapper;
    private final ApiExplorer apiExplorer;
    private final TourRepository tourRepository;

    public void saveTour(String contentId) throws URISyntaxException, JsonProcessingException {
        Api apiData = apiExplorer.getData(contentId);
        tourMapper.insertTourList(apiData);
    }

    @Override
    public List<Attraction> tourList(List<String> areaCode) {
        return tourRepository.tourList(areaCode);
    }
}
