package com.example.http.service;

import com.example.http.common.ConstantConfig;
import com.example.http.util.AESCipher;
import com.example.http.util.DisableSSLCertificateCheckUtil;
import org.apache.commons.codec.DecoderException;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import static java.awt.SystemColor.text;

/**
 * Created by WT on 2017/9/13.
 */

public class WebServiceHttp {

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param,String coding) {
        String result = "";
        BufferedReader in = null;
        String urlNameString="";
        try {
            if(param!=null&&!param.isEmpty()){
                StringBuilder builder=new StringBuilder();
                String[] params=param.split("&");
                for (String str:params
                        ) {
                    String[] strings=str.split("=");
                    String encoder="";
                    if(strings.length>=2){
                        encoder=URLEncoder.encode(strings[1],"UTF-8");
                    }else{
                        encoder=URLEncoder.encode("","UTF-8");
                    }
                    builder.append(strings[0]).append("=").append(encoder).append("&");
                }
                builder.delete(builder.length()-1,builder.length());
                param=builder.toString();
                urlNameString = url + "?" + param;
            }else{
                urlNameString=url;
            }
//            DisableSSLCertificateCheckUtil.disableChecks();
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Accept-Charset", coding);
            connection.setRequestProperty("contentType", coding);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),coding));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param,String coding) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            if(param!=null&&!param.isEmpty()){
                StringBuilder builder=new StringBuilder();
                String[] params=param.split("&");
                for (String str:params
                        ) {
                    String[] strings=str.split("=");
                    String encoder="";
                    if(strings.length>=2){
                        encoder=URLEncoder.encode(strings[1],"UTF-8");
                    }else{
                        encoder=URLEncoder.encode("","UTF-8");
                    }
                    builder.append(strings[0]).append("=").append(encoder).append("&");
                }
                builder.delete(builder.length()-1,builder.length());
                param=builder.toString();
            }else{
                param=url;
            }
            DisableSSLCertificateCheckUtil.disableChecks();
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", coding);
            conn.setRequestProperty("contentType", coding);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),coding));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


}
