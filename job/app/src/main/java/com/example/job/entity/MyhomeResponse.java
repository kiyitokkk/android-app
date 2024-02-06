package com.example.job.entity;

public class MyhomeResponse {



    private String msg;
    private int code;
    private DatasBean datas;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * headurl : https://p3-pc.douyinpic.com/img/aweme-avatar/tos-cn-i-0813_9d0c5b8dd8d44abea0b51d94d634690f~c5_168x168.jpeg?from=2956013662
         * userid : 123132
         * username : chen
         */

        private String headurl;
        private String userid;
        private String username;

        public String getHeadurl() {
            return headurl;
        }

        public void setHeadurl(String headurl) {
            this.headurl = headurl;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
