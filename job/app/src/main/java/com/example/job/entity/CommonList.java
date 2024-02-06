package com.example.job.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonList {


    @JsonProperty("code")
    private Integer code;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("commentlist")
    private List<CommentlistDTO> commentlist;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CommentlistDTO> getCommentlist() {
        return commentlist;
    }

    public void setCommentlist(List<CommentlistDTO> commentlist) {
        this.commentlist = commentlist;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CommentlistDTO {
        @JsonProperty("avatar")
        private String avatar;
        @JsonProperty("content")
        private String content;
        @JsonProperty("upload_time")
        private String upload_time;
        @JsonProperty("username")
        private String username;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUploadTime() {
            return upload_time;
        }

        public void setUploadTime(String upload_time) {
            this.upload_time = upload_time;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
