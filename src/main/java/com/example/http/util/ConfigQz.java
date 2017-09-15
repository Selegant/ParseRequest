package com.example.http.util;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * Created by WT on 2017/9/15.
 */

@ConfigurationProperties(prefix="configQz")
@PropertySource("classpath:config/config.properties")
public class ConfigQz extends Config {

    @Autowired
    Environment evo;

    public ConfigQz() {
        this.aesKey = evo.getProperty("configQz.aesKey");
        this.aesIv = evo.getProperty("configQz.aesIv");
    }
}
