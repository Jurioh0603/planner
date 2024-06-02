package com.triplan.planner.mypage.dto;

import lombok.Data;

import java.util.List;

@Data
public class MyCommunityPage {
    private int total;
    private int currentPage;
    private List<MyCommunityList> myCommunityList;
    private int totalPages;
    private int startPage;
    private int endPage;
    private int myTotal;

    public MyCommunityPage(int total, int currentPage, int size, List<MyCommunityList> myCommunityList) {
        this.total = total;
        this.currentPage = currentPage;
        this.myCommunityList = myCommunityList;
        if(total == 0) {
            totalPages = 0;
            startPage = 0;
            endPage = 0;
        } else {
            totalPages = total / size;
            if(total % size > 0) {
                totalPages++;
            }
            int modVal = currentPage % 5;
            startPage = currentPage / 5 * 5 + 1;
            if(modVal == 0) startPage -= 5;

            endPage = startPage + 4;
            if(endPage > totalPages) endPage = totalPages;
        }
    }

    public boolean hasNoContents() {
        return total == 0;
    }

    public boolean hasContents() {
        return total > 0;
    }
}
