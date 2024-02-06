package com.example.job.api;

public interface RequestCallback {
    void onSucess(String res);
    void onFailure(Exception err);
}
