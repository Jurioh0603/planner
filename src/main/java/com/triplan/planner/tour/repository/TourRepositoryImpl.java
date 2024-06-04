package com.triplan.planner.tour.repository;

import com.triplan.planner.tour.dto.Attraction;
import com.triplan.planner.tour.dto.AttractionDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TourRepositoryImpl implements TourRepository{

    private final TourMapper tourMapper;
    @Override
    public List<Attraction> tourList(List<String> areaCode, int startRow, int size, String search) {
        return tourMapper.tourList(areaCode, startRow, size, search);
    }

    @Override
    public int attractionCount(List<String> areaCode, String search) {
        return tourMapper.attractionCount(areaCode, search);
    }

    @Override
    public AttractionDetail getTourDetail(int placeNo) {
        return tourMapper.getTourDetail(placeNo);
    }
}
