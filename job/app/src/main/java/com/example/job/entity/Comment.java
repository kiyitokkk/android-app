package com.example.job.entity;

public class Comment {
    private String username;
    private String commentText;
    private String avatar;
    private String uploadTime;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Comment(String username, String commentText, String avatar, String uploadTime) {
        this.username = username;
        this.commentText = commentText;
        this.avatar = avatar;
        this.uploadTime = uploadTime;
    }

    public String getUsername() {
        return username;
    }

    public String getCommentText() {
        return commentText;
    }
}