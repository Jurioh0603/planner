package com.triplan.planner.tour.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDetail {

    private int placeNo;
    private String placeName;
    private String placeAddress;
    private String placeAreaCode;
    private String placeTel;
    private String placePage;
    private String placeIntro;
    private String placeImg;
}
