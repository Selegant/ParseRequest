package com.example.http.controller;

import com.example.http.service.WebServiceHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;

/**
 * Created by WT on 2017/9/13.
 */
@Controller
@ComponentScan("com.example.http")
public class ParseRquest {
    private Logger logger = LoggerFactory.getLogger(ParseRquest.class);


    @RequestMapping(value = "/runApi")
    public String runApi() {
        return "RunApi";
    }


    @RequestMapping(value = "/sendPost")
    @ResponseBody
    public String sendPost(String url){
        System.out.println(url);
        StringBuilder builder=new StringBuilder();
        String[] urls=url.split("\\?");
        builder.append("**请求URL：** ");
        builder.append("<br/>");
        builder.append("- ` "+urls[0]+" `");
        builder.append("<br/><br/>");
        builder.append("**请求方式：**");
        builder.append("<br/>");
        builder.append("- POST ");
        builder.append("<br/><br/>");
        builder.append("**参数：**");
        builder.append("<br/><br/>");
        builder.append("|参数名|必选|类型|说明|");
        builder.append("<br/>");
        builder.append("|:----    |:---|:----- |-----   |");
        builder.append("<br/>");
        String[] params=urls[1].split("&");
        for (String param:params
             ) {
            String[] str=param.split("=");
            builder.append("|   ").append(str[0]).append("   |   ");
            builder.append("是").append("    |   ");
            builder.append("string").append("   |   ");
            builder.append("无").append("    |   ");
            builder.append("<br/>");
        }
        try {
            builder.append("<br/><br/>");
            builder.append("**返回示例**");
            builder.append("<br/><br/>");
            builder.append(url);
            builder.append("<br/><br/>");
            builder.append("```");
            builder.append("<br/><br/>");
            String result=WebServiceHttp.readContentFromPost(urls[0],urls[1]);
            builder.append(formatJson(result));
            builder.append("<br/><br/>");
            builder.append("```");
            builder.append("<br/>");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(builder.toString());

        return  builder.toString();
    }

    public static void main(String[] args) throws Exception {
        String url="https://api.weixin.qq.com/cgi-bin/token";
        String param="grant_type=client_credential&appid=wx02c813fe80e4cadb&secret=4d5eb3c0b331821da0145fd7812944e6";
        String result=WebServiceHttp.sendGet(url,param);
        System.out.println(result);
    }

    /**
     * 格式化json
     * @param content
     * @return
     */
    public static String formatJson(String content) {
        StringBuffer sb = new StringBuffer();
        int index = 0;
        int count = 0;
        while(index < content.length()){
            char ch = content.charAt(index);
            if(ch == '{' || ch == '['){
                sb.append(ch);
                sb.append("<br/>");
                count++;
                for (int i = 0; i < count; i++) {
                    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                }
            }
            else if(ch == '}' || ch == ']'){
                sb.append("<br/>");
                count--;
                for (int i = 0; i < count; i++) {
                    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                }
                sb.append(ch);
            }
            else if(ch == ','){
                sb.append(ch);
                sb.append("<br/>");
                for (int i = 0; i < count; i++) {
                    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                }
            }
            else {
                sb.append(ch);
            }
            index ++;
        }
        return sb.toString();
    }
}
