package com.ait.app.requestbody;

import java.util.List;

public class FeedbackPageResponseDto {

    private List<FeedbackResponseDto> feedback;
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private int pageSize;

    public List<FeedbackResponseDto> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<FeedbackResponseDto> feedback) {
        this.feedback = feedback;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
