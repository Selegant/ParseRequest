package com.example.http.controller;

import com.example.http.service.Analysis;
import com.example.http.service.HttpRequest;
import com.example.http.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * Created by WT on 2017/9/13.
 */
@Controller
@ComponentScan("com.example.http")
@PropertySource("classpath:config/config.properties")
public class ParseRquest {
    private Logger logger = LoggerFactory.getLogger(ParseRquest.class);
    @Autowired
    Config config;
    @Autowired
    HttpRequest request;
    @Autowired
    Analysis analysis;


    @RequestMapping(value = "/runApi")
    public String runApi(Model model) {
        return "RunApi";
    }


    @RequestMapping(value = "/run")
    @ResponseBody
    public String run(Model model) {
        String s= "{\"device_type\":\"2\",\"app_version\":\"1.0\",\"access_token\":\"4647b4f7-1d23-4fbe-907e-af68bd4b6732\"}";
        config=new Config();
        try {
            s=analysis.enCode(s,config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     *
     * @param methodType  1 get请求 2 post请求
     * @param type   0 不加密  1 黄山加密 2 衢州加密
     * @param url
     * @param coding  GBK  UTF-8
     */
    @RequestMapping(value = "/sendRequest")
    @ResponseBody
    public String sendRequest(String methodType, String type, String url, String coding, String arguements) {
        String result = "";
        try {
            if ("1".equals(methodType)) {
                result = request.sendGet(url, coding, arguements,type);
                request.switchType(type,result);
            } else if ("2".equals(methodType)) {
                result = request.sendPost(url, coding, arguements,type);
                request.switchType(type,result);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        finally {
            return result;
        }
    }

}
