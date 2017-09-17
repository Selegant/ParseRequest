package com.example.http.service.impl;

import com.example.http.service.HttpRequest;
import com.example.http.service.WebServiceHttp;
import com.example.http.util.AESCipher;
import com.example.http.util.Template;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Created by WT on 2017/9/15.
 */
@Service
public class HttpRequestImpl implements HttpRequest{

    private String qzAesKey="Hu08an_gDSha_n23";

    private String qzAesIv="Da08_Dbay23_Bhan";

    private String hsAeskey="Hu08an_gDSha_n23";

    private String hsAesIv="Da08_Dbay23_Bhan";

    @Override
    public String sendGet(String url, String coding,String arguements,String type) {
        System.out.println(url);
        String sendUrl="";
        String argument="";
        if(url.contains("?")){
            String[] urls=url.split("\\?");
            sendUrl=urls[0];
            argument=urls[1];
        }else{
            sendUrl=url;
        }
        String sendType="GET";
        String result= WebServiceHttp.sendGet(sendUrl,argument,coding);
        try {
            if("1".equals(type)){
                JSONObject object=new JSONObject(result);
                result=object.getString("dataBack");
                result= AESCipher.aesDecryptString(result,hsAeskey,hsAesIv);
            }else if("2".equals(type)){
                JSONObject object=new JSONObject(result);
                result=object.getString("dataBack");
                result=AESCipher.aesDecryptString(result,qzAesKey,qzAesIv);
            }
        }catch (Exception e){
            e.fillInStackTrace();
        }
        result= Template.template(url,sendUrl,arguements,sendType,result);
        return result;
    }

    @Override
    public String sendPost(String url, String coding,String arguements,String type) {
        System.out.println(url);
        String sendUrl="";
        String argument="";
        if(url.contains("?")){
            String[] urls=url.split("\\?");
            sendUrl=urls[0];
            argument=arguements;
        }else{
            sendUrl=url;
        }
        String sendType="POST";
        String result=WebServiceHttp.sendPost(sendUrl,argument,coding);
        try {
            if("1".equals(type)){
                JSONObject object=new JSONObject(result);
                result=object.getString("dataBack");
                result= AESCipher.aesDecryptString(result,hsAeskey,hsAesIv);
            }else if("2".equals(type)){
                JSONObject object=new JSONObject(result);
                result=object.getString("dataBack");
                result=AESCipher.aesDecryptString(result,qzAesKey,qzAesIv);
            }
        }catch (Exception e){
            e.fillInStackTrace();
        }
        result=Template.template(url,sendUrl,argument,sendType,result);
        return result;
    }
}
