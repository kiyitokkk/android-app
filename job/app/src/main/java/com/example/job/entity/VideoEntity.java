package com.example.job.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoEntity implements Serializable {
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    private String categoryId;

    private int vid;
    private String vtitle;
    private String author;
    private String coverurl;
    private String headurl;
    private int commentNum;
    private int likeNum;
    private int collectNum;
    private boolean flagLike;
    private boolean flagCollect;
    private String upload_time;
    public String getPlayurl() {
        return playurl;
    }

    public void setPlayurl(String playurl) {
        this.playurl = playurl;
    }

    private String playurl;

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getVtitle() {
        return vtitle;
    }

    public void setVtitle(String vtitle) {
        this.vtitle = vtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverurl() {
        return coverurl;
    }


    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }
    public boolean isFlagLike() {
        return flagLike;
    }

    public void setFlagLike(boolean flagLike) {
        this.flagLike = flagLike;
    }

    public boolean isFlagCollect() {
        return flagCollect;
    }

    public void setFlagCollect(boolean flagCollect) {
        this.flagCollect = flagCollect;
    }


    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }
}
