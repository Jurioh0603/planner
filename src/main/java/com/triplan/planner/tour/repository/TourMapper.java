package com.triplan.planner.tour.repository;

import com.triplan.planner.tour.dto.Api;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TourMapper {
    void insertTourList(Api api);
}
