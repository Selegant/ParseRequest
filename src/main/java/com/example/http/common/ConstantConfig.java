package com.example.http.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ConfigurationProperties(prefix = "config")
@PropertySource("classpath:config/config.properties")
public class ConstantConfig {

    @Value("${config.hsAesKey}")
	private  String hsAesKey;
    @Value("${config.hsAesIv}")
    private  String hsAesIv;
    @Value("${config.qzAesKey}")
    private  String qzAesKey;
    @Value("${config.qzAesIv}")
    private  String qzAesIv;


    public String getHsAesKey() {
        return hsAesKey;
    }

    public void setHsAesKey(String hsAesKey) {
        this.hsAesKey = hsAesKey;
    }

    public String getHsAesIv() {
        return hsAesIv;
    }

    public void setHsAesIv(String hsAesIv) {
        this.hsAesIv = hsAesIv;
    }

    public String getQzAesKey() {
        return qzAesKey;
    }

    public void setQzAesKey(String qzAesKey) {
        this.qzAesKey = qzAesKey;
    }

    public String getQzAesIv() {
        return qzAesIv;
    }

    public void setQzAesIv(String qzAesIv) {
        this.qzAesIv = qzAesIv;
    }
}
