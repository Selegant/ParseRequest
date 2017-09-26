package com.example.http.aop;

import com.example.http.common.ConstantConfig;
import com.example.http.util.AESCipher;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.commons.collections.map.HashedMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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

    @Before("sendRequest()")
    public void logParam(JoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        String mothodName=pjp.getSignature().getName();
        String classType = pjp.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String methodName = pjp.getSignature().getName();
        Map<String,Object> paramNames = getFieldsName(this.getClass(), clazzName, methodName,args);
        logger.info(mothodName+"方法入参为"+paramNames.toString());
    }


    @Around("sendRequest()")
    public Object brfore(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
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


    private Map<String,Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String,Object > map=new HashMap<String,Object>();

        ClassPool pool = ClassPool.getDefault();
        //ClassClassPath classPath = new ClassClassPath(this.getClass());
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++){
            map.put( attr.variableName(i+pos+1),args[i]);//paramNames即参数名
        }
        //Map<>
        return map;
    }
}
