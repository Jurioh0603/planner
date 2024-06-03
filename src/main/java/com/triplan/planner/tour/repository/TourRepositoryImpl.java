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
    public List<Attraction> tourList(List<String> areaCode, int startRow, int size) {
        return tourMapper.tourList(areaCode, startRow, size);
    }

    @Override
    public int attractionCount(List<String> areaCode) {
        return tourMapper.attractionCount(areaCode);
    }

    @Override
    public AttractionDetail getTourDetail(int placeNo) {
        return tourMapper.getTourDetail(placeNo);
    }
}
