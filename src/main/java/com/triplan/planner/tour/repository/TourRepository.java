package com.triplan.planner.tour.repository;

import com.triplan.planner.tour.dto.Attraction;
import com.triplan.planner.tour.dto.AttractionDetail;

import java.util.List;

public interface TourRepository {
    public List<Attraction> tourList(List<String> areaCode, int startRow, int size, String search);

    public int attractionCount(List<String> areaCode, String search);

    public AttractionDetail getTourDetail(int placeNo);
}
