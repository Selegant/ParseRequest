package com.example.http.aop;

import com.example.http.common.ConstantConfig;
import com.example.http.util.AESCipher;
import org.apache.commons.collections.map.HashedMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;


/**
 * Created by Selegant on 2017/9/16.
 */

@Component
@Aspect
public class ParamAspect {
    private static final Logger logger = LoggerFactory.getLogger(ParamAspect.class);
    @Autowired
    ConstantConfig config;


    @Pointcut("execution(* com.example.http.controller.ParseRquest.sendRequest(..))")
    public void sendRequest() {

    }


    @Around("sendRequest()")
    public Object brfore(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for(Object obj : args){
            System.out.println("arguments: "+obj);
        }
        String type = (String)args[1];
        String url=(String)args[2];
        if("0".equals(type)) {
            if (url.contains("?")) {
                String[] urls = url.split("\\?");
                args[4] = urls[1];
            }
        }else if("1".equals(type)){
            if(url.contains("?")){
                String[] urls=url.split("\\?");
                args[4]=urls[1];
                String[] params=urls[1].split("&");
                Map<String,Object> paramMap=new HashedMap();
                for (String param:params
                     ) {
                    String[] values=param.split("=");
                    paramMap.put(values[0],values[1]);
                }
                JSONObject object=new JSONObject(paramMap);
                String cipher= AESCipher.aesEncryptString(object.toString(), config.getHsAesKey(),config.getHsAesIv());
                url=urls[0]+"?param="+cipher;
                args[2] = url;
            }
        }
        if("2".equals(type)){
            if(url.contains("?")){
                String[] urls=url.split("\\?");
                args[4]=urls[1];
                String[] params=urls[1].split("&");
                Map<String,Object> paramMap=new HashedMap();
                for (String param:params
                        ) {
                    String[] values=param.split("=");
                    paramMap.put(values[0],values[1]);
                }
                JSONObject object=new JSONObject(paramMap);
                String cipher= AESCipher.aesEncryptString(object.toString(),config.getQzAesKey(),config.getQzAesIv());
                url=urls[0]+"?param="+cipher;
                args[2] = url;
            }
        }
        Object retVal = pjp.proceed(args);
        return retVal;
    }


}
