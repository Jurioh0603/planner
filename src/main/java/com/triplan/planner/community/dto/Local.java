package com.triplan.planner.community.dto;

public enum Local {
    S("서울"),
    GG("경기"),
    GW("강원"),
    CB("충북"),
    CN("충남"),
    JB("전북"),
    JN("전남"),
    GB("경북"),
    GN("경남"),
    JJ("제주");

    public final String name;

    private Local(String name) {
        this.name = name;
    }
}
