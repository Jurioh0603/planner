package com.triplan.planner.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attravtion {
    private int placeNo;
    private String placeName;
    private String placeAddress;
    private String placeIntro;
    private String placeImg;
    private String placeCopyImg;

}
