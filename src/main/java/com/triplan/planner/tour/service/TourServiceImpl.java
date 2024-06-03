package com.triplan.planner.tour.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.triplan.planner.tour.controller.ApiExplorer;
import com.triplan.planner.tour.dto.Api;
import com.triplan.planner.tour.dto.Attraction;
import com.triplan.planner.tour.dto.AttractionDetail;
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
    public List<Attraction> tourList(List<String> areaCode, int page) {
        int size = 6;
        return tourRepository.tourList(areaCode,(page - 1) * size, size);
    }

    @Override
    public int attractionCount(List<String> areaCode) {
        return tourRepository.attractionCount(areaCode);
    }

    @Override
    public AttractionDetail getTourDetail(int placeNo) {
        return tourRepository.getTourDetail(placeNo);
    }
}
