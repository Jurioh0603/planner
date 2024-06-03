package com.triplan.planner.tour.repository;

import com.triplan.planner.tour.dto.Api;
import com.triplan.planner.tour.dto.Attraction;
import com.triplan.planner.tour.dto.AttractionDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TourMapper {
    public void insertTourList(Api api);
    public List<Attraction> tourList(@Param("areaCode") List<String> areaCode,
                                     @Param("startRow") int startRow,
                                     @Param("size") int size);

    public int attractionCount(List<String> areaCode);

    public AttractionDetail getTourDetail(int placeNo);
}
