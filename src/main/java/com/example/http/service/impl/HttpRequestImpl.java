package com.example.http.service.impl;

import com.example.http.service.HttpRequest;
import com.example.http.service.WebServiceHttp;
import com.example.http.util.Template;
import org.springframework.stereotype.Service;

/**
 * Created by WT on 2017/9/15.
 */
@Service
public class HttpRequestImpl implements HttpRequest{

    @Override
    public String sendGet(String url, String coding) {
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
        String result= WebServiceHttp.sendGet(sendUrl,arguments,coding);
        result= Template.template(url,sendUrl,arguments,type,result);
        return result;
    }

    @Override
    public String sendPost(String url, String coding) {
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
        result=Template.template(url,sendUrl,arguments,type,result);
        return result;
    }
}
