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
    public String runApi(Model model) {
        return "RunApi";
    }


    @RequestMapping(value = "/sendGet")
    @ResponseBody
    public String sendGet(String url,String coding){
        System.out.println(url);
        String sendUrl="";
        String arguments="";
        if(url.contains("?")){
            String[] urls=url.split("\\?");
            sendUrl=urls[0];
            arguments=urls[1];
        }else{
            sendUrl=url;
        }
        String type="GET";
        String result=WebServiceHttp.sendGet(sendUrl,arguments,coding);
        result=template(url,sendUrl,arguments,type,result);
        return result;
    }

    @RequestMapping(value = "/sendPost")
    @ResponseBody
    public String sendPost(String url,String coding){
        System.out.println(url);
        String sendUrl="";
        String arguments="";
        if(url.contains("?")){
            String[] urls=url.split("\\?");
            sendUrl=urls[0];
            arguments=urls[1];
        }else{
            sendUrl=url;
        }
        String type="POST";
        String result=WebServiceHttp.sendPost(sendUrl,arguments,coding);
        result=template(url,sendUrl,arguments,type,result);
        return result;
    }

    public static String template(String url,String sendUrl,String arguments,String type,String result){
        StringBuilder builder=new StringBuilder();
        builder.append("**请求URL：** ");
        builder.append("<br/>");
        builder.append("- ` "+sendUrl+" `");
        builder.append("<br/><br/>");
        builder.append("**请求方式：**");
        builder.append("<br/>");
        builder.append("- "+type+" ");
        builder.append("<br/><br/>");
        builder.append("**参数：**");
        builder.append("<br/><br/>");
        builder.append("|参数名|必选|类型|说明|");
        builder.append("<br/>");
        builder.append("|:----    |:---|:----- |-----   |");
        builder.append("<br/>");
        String[] params=arguments.split("&");
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
            builder.append("<xmp>");
            builder.append(url);
            builder.append("\n\n");
            builder.append("</xmp>");
            builder.append("```");
            builder.append("<xmp>");
            builder.append("\n\n");
            builder.append(formatJson(result));
            builder.append("</xmp>");
            builder.append("<br/><br/>");
            builder.append("```");
            builder.append("<br/>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  builder.toString();
    }


    public static String template(String url,String sendUrl,String type,String result){
        StringBuilder builder=new StringBuilder();
        builder.append("**请求URL：** ");
        builder.append("<br/>");
        builder.append("- ` "+sendUrl+" `");
        builder.append("<br/><br/>");
        builder.append("**请求方式：**");
        builder.append("<br/>");
        builder.append("- "+type+" ");
        builder.append("<br/><br/>");
        try {
            builder.append("<br/><br/>");
            builder.append("**返回示例**");
            builder.append("<br/><br/>");
            builder.append("<xmp>");
            builder.append(url);
            builder.append("\n\n");
            builder.append("</xmp>");
            builder.append("```");
            builder.append("<xmp>");
            builder.append("\n\n");
            builder.append(formatJson(result));
            builder.append("</xmp>");
            builder.append("<br/><br/>");
            builder.append("```");
            builder.append("<br/>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  builder.toString();
    }


    public static void main(String[] args) throws Exception {
        String param="user=341000&pass=qLb930mVRq&aac002=342401199010178515&aac003=查全义&aab301=3670";
        StringBuilder builder=new StringBuilder();
        String[] params=param.split("&");
        for (String str:params
                ) {
            String[] strings=str.split("=");
            String encoder=URLEncoder.encode(strings[1],"UTF-8");
            builder.append(strings[0]).append("=").append(encoder).append("&");
        }
        builder.delete(builder.length()-1,builder.length());
        System.out.println(builder.toString());
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
                sb.append("\n");
                count++;
                for (int i = 0; i < count; i++) {
                    sb.append("\t");
                }
            }
            else if(ch == '}' || ch == ']'){
                sb.append("\n");
                count--;
                for (int i = 0; i < count; i++) {
                    sb.append("\t");
                }
                sb.append(ch);
            }
            else if(ch == ','){
                sb.append(ch);
                sb.append("\n");
                for (int i = 0; i < count; i++) {
                    sb.append("\t");
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
