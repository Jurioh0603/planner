package com.triplan.planner.mypage.dto;

import lombok.Data;

import java.util.List;

@Data
public class MyTlogPage {
    private int total;
    private int currentPage;
    private List<MyTlogList> myTlogLists;
    private int totalPages;
    private int startPage;
    private int endPage;

    public MyTlogPage(int total, int currentPage, int size, List<MyTlogList> myTlogList) {
        this.total = total;
        this.currentPage = currentPage;
        this.myTlogLists = myTlogList;
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
