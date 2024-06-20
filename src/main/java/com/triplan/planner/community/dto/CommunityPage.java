package com.triplan.planner.community.dto;

import lombok.Data;

@Data
public class CommunityPage {

    private int total;
    private int currentPage;
    private CommunityList communityList;
    private int totalPages;
    private int startPage;
    private int endPage;

    public CommunityPage(int total, int currentPage, int size, CommunityList communityList) {
        this.total = total;
        this.currentPage = currentPage;
        this.communityList = communityList;
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
