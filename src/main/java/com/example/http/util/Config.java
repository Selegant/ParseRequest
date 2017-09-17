package com.example.http.util;

import org.springframework.stereotype.Component;

/**
 * Created by WT on 2017/9/15.
 */
@Component
public class Config {
    public String aesKey;
    public String aesIv;

    public Config() {
    }

    public Config(String aesKey, String aesIv) {
        this.aesKey = aesKey;
        this.aesIv = aesIv;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getAesIv() {
        return aesIv;
    }

    public void setAesIv(String aesIv) {
        this.aesIv = aesIv;
    }
}
