package com.example.http.service;

/**
 * Created by WT on 2017/9/15.
 */
public interface HttpRequest {
    public String sendGet(String url,String coding);
    public String sendPost(String url,String coding);
}
