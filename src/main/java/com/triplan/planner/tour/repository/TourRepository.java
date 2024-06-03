package com.triplan.planner.tour.repository;

import com.triplan.planner.tour.dto.Attraction;

import java.util.List;

public interface TourRepository {
    public List<Attraction> tourList(List<String> areaCode);
}
