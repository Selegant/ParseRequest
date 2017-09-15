package com.example.http.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * Created by WT on 2017/9/15.
 */

//@ConfigurationProperties(prefix = "configHs")
@PropertySource("classpath:config/config.properties")
public class ConfigHs extends Config {

    @Autowired
    Environment evo;

    public ConfigHs() {
        this.aesKey = evo.getProperty("configHs.aesKey");
        this.aesIv = evo.getProperty("configHs.aesIv");
    }
}
