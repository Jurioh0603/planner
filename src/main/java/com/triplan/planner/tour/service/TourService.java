package com.triplan.planner.tour.service;

import com.triplan.planner.tour.dto.Api;
import com.triplan.planner.tour.repository.TourMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourService {

    @Autowired
    private TourMapper tourMapper;

    public void saveTourList(Api api) {
        tourMapper.insertTourList(api);
    }
}
