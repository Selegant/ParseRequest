package com.example.http.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConstantConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(ConstantConfig.class);
	/**
	 * 添加其他参保人的人数限制
	 */
	 public static String hsAesKey = "";
	 public static String hsAesIv = "";
	 public static String qzAesKey = "";
	 public static String qzAesIv = "";

    static {   
        try {
			hsAesKey = ServerConfig.getProertiesValue("hsAesKey");
			hsAesIv = ServerConfig.getProertiesValue("hsAesIv");
			qzAesKey = ServerConfig.getProertiesValue("qzAesKey");
			qzAesIv = ServerConfig.getProertiesValue("qzAesIv");
        	logger.info("读取静态代码块配置");
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
}
