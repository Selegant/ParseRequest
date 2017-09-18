/**  
 实现对Java配置文件Properties的读取、写入与更新操作  
*/   
package com.example.http.common;
  
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileInputStream;   
import java.io.FileOutputStream;   
import java.io.IOException;   
import java.io.InputStream;   
import java.io.OutputStream;
import java.util.Properties;   
  
  
/**  
* @author  
* @version  
*/
@Component
public class ServerConfig {

    static String profilepath="F:\\ParseRequest\\ParseRequest\\src\\main\\resources\\config\\config.properties";


    /**
    * 采用静态方
    */   
    private static Properties props = new Properties();
    static {   
        try {
            props.load(new FileInputStream(profilepath));   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
  
    /**  
    * 读取属性文件中相应键的
    * @param key  
    *            主键  
    * @return String  
    */   
    public static String getKeyValue(String key) {   
        return props.getProperty(key);   
    }   
  
    /**  
    * 根据主键key读取主键的值value
    * @param key 键名  
    */   
    public static String getProertiesValue(String key) {   
        Properties props = new Properties();   
        try {   
            InputStream in = new BufferedInputStream(new FileInputStream(   
            		profilepath));   
            props.load(in);   
            String value = props.getProperty(key);   
            System.out.println(key +"键的值是"+ value);   
            return value;   
        } catch (Exception e) {   
            e.printStackTrace();   
            return null;   
        }   
    }   
      
    /**  
    * 更新（或插入）一对properties信息(主键及其键 
    * 如果该主键已经存在，更新该主键的值；  
    * 如果该主键不存在，则插件一对键值
    * @param keyname 键名  
    * @param keyvalue 键
    */   
    public static void setProperties(String keyname,String keyvalue) {          
        try {   
            OutputStream fos = new FileOutputStream(profilepath);   
            props.setProperty(keyname, keyvalue);   
            props.store(fos, "Update '" + keyname + "' value");   
        } catch (IOException e) {   
            System.err.println("属性文件更新错误");   
        }   
    }   
  
    /**  
    * 更新properties文件的键值对  
    */   
    public void updateProperties(String keyname,String keyvalue) {   
        try {   
            props.load(new FileInputStream(profilepath));   
            OutputStream fos = new FileOutputStream(profilepath);              
            props.setProperty(keyname, keyvalue);   
            props.store(fos, "Update '" + keyname + "' value");   
        } catch (IOException e) {   
        	 System.err.println("属性文件更新错误");   
        }   
    }   
}   
