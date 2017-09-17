package com.example.http.controller;

import com.example.http.service.Analysis;
import com.example.http.service.HttpRequest;
import com.example.http.util.AESCipher;
import com.example.http.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("quAesKey")
    private String qzAesKey;
    @Value("qzAesIv")
    private String qzAesIv;
    @Value("hsAeskey")
    private String hsAeskey;
    @Value("hsAesIv")
    private String hsAesIv;

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
                switch (type) {
                    case "0":
                    case "1":
                        result = AESCipher.aesDecryptString(result, hsAeskey, hsAesIv);
                    case "2":
                        result = AESCipher.aesDecryptString(result, qzAesKey, qzAesIv);
                    default:
                        break;
                }
            } else if ("2".equals(methodType)) {
                result = request.sendPost(url, coding, arguements,type);
                switch (type) {
                    case "0":
                    case "1":
                        result = AESCipher.aesDecryptString(result, hsAeskey, hsAesIv);
                    case "2":
                        result = AESCipher.aesDecryptString(result, qzAesKey, qzAesIv);
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        finally {
            return result;
        }
    }





    public static void main(String[] args) throws Exception {
//        String param="user=341000&pass=qLb930mVRq&aac002=342401199010178515&aac003=查全义&aab301=3670";
//        StringBuilder builder=new StringBuilder();
//        String[] params=param.split("&");
//        for (String str:params
//                ) {
//            String[] strings=str.split("=");
//            String encoder=URLEncoder.encode(strings[1],"UTF-8");
//            builder.append(strings[0]).append("=").append(encoder).append("&");
//        }
//        builder.delete(builder.length()-1,builder.length());
//        System.out.println(builder.toString());
    }


}
