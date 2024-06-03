package com.triplan.planner.tour.repository;

import com.triplan.planner.tour.dto.Attraction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TourRepositoryImpl implements TourRepository{

    private final TourMapper tourMapper;
    @Override
    public List<Attraction> tourList(List<String> areaCode) {
        return tourMapper.tourList(areaCode);
    }
}
