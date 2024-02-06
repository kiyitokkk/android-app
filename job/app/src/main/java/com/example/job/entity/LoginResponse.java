package com.example.job.entity;

public class LoginResponse {

    /**
     * code : 0
     * expire : 123456
     * msg : sucess
     * token : dawdawdaghdkjawhdkjagwdjkawghjdawjdawdaw
     */

    private int code;
    private String expire;
    private String msg;
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
