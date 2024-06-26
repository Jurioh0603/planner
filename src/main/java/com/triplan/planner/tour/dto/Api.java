package com.triplan.planner.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api {
    private String title;
    private String addr1;
    private String areacode;
    private String tel;
    private String homepage;
    private String overview;
    private String firstimage;
}
