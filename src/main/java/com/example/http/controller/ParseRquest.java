package com.example.http.controller;

import com.example.http.bean.ResultSupport;
import com.example.http.common.ConstantConfig;
import com.example.http.service.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by WT on 2017/9/13.
 */
@Controller
@ComponentScan("com.example.http")
public class ParseRquest {
    private Logger logger = LoggerFactory.getLogger(ParseRquest.class);
    @Autowired
    ConstantConfig config;
    @Autowired
    HttpRequest request;


    @RequestMapping(value = "/runApi")
    public String runApi(Model model) {
        List<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        model.addAllAttributes(list);
        return "RunApi";
    }


    @RequestMapping(value = "/run")
    @ResponseBody
    public String run(Model model) {
        System.out.println(config.getHsAesIv());
        return config.getHsAesIv();
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
    public String sendRequest(
            String methodType,
            String type, String url,
            String coding,
            String arguements) {
        String result = "";
        try {
            if ("1".equals(methodType)) {
                result = request.sendGet(url, coding, arguements,type);
                request.switchType(type,result);
            } else if ("2".equals(methodType)) {
                result = request.sendPost(url, coding, arguements, type);
                request.switchType(type, result);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return  result;
    }

}
