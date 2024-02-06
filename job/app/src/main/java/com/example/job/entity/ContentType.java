package com.example.job.entity;

public class ContentType {
    private int categoryId;
    private String categoryName;

    public ContentType(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
