package com.example.http.service.impl;

import com.example.http.common.ConstantConfig;
import com.example.http.service.HttpRequest;
import com.example.http.service.WebServiceHttp;
import com.example.http.util.AESCipher;
import com.example.http.util.Template;
import org.apache.commons.codec.DecoderException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by WT on 2017/9/15.
 */
@Service
public class HttpRequestImpl implements HttpRequest{

    @Autowired
    ConstantConfig config;

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
            result=decryptString(type,result);
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
            argument=urls[1];
        }else{
            sendUrl=url;
        }
        String sendType="POST";
        String result=WebServiceHttp.sendPost(sendUrl,argument,coding);
        try {
            result=decryptString(type,result);
        }catch (Exception e){
            e.fillInStackTrace();
        }
        result=Template.template(url,sendUrl,arguements,sendType,result);
        return result;
    }

    public String decryptString(String type,String result) throws NoSuchPaddingException, DecoderException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException, JSONException {
        if("1".equals(type)){
            JSONObject object=new JSONObject(result);
            result=object.getString("dataBack");
            result= AESCipher.aesDecryptString(result,config.getHsAesKey(),config.getHsAesIv());
        }else if("2".equals(type)){
            JSONObject object=new JSONObject(result);
            result=object.getString("dataBack");
            result=AESCipher.aesDecryptString(result,config.getQzAesKey(),config.getQzAesIv());
        }
        return result;
    }

    @Override
    public String switchType(String type, String result) throws NoSuchPaddingException, DecoderException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {
        switch (type) {
            case "0":
                return  result;
            case "1":
                result = AESCipher.aesDecryptString(result, config.getHsAesKey(), config.getHsAesIv());
                return  result;
            case "2":
                result = AESCipher.aesDecryptString(result, config.getQzAesKey(), config.getQzAesIv());
                return  result;
            default:
                return  result;
        }
    }
}
