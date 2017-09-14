package com.example.http.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class APIAuto {

    public static void main(String argv[]) throws Exception {
        updateSql("E://source.txt");

    }

    public static void updateSql(String filePath) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("**简要描述：**");
        sb.append("\n");
        sb.append("\n");


        String encoding = "GBK";
        File file = new File(filePath);
        if (file.isFile() && file.exists()) { //
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            //读取接口名称
            int a1 = 1, a2 = 1, a3 = 1, a4 = 1;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                if (lineTxt == null || lineTxt.length() < 5) {
                    continue;
                }
                if (a1 == 1) {//读取接口名称

                    sb.append(lineTxt);
                    sb.append("\n");
                    sb.append("\n");
                    sb.append("**请求URL：**");
                    sb.append("\n");

                }

                if (lineTxt == null || lineTxt.indexOf("接口名称") != -1) {
                    a1 = 100;
                }
                a1++;
                if (a1 == 102) {//读取接口名称

                    sb.append(lineTxt.replaceAll("AAAA", ""));
                    sb.append("\n");
                    sb.append("\n");
                    sb.append("**请求方式：**");
                    sb.append("\n");
                    sb.append("- POST");
                    sb.append("\n");
                    sb.append("\n");
                }

                if (lineTxt == null || lineTxt.indexOf("请求消息") != -1) {
                    a2 = 200;
                }
                a2++;
                if (lineTxt == null || lineTxt.indexOf("响应消息") != -1) {
                    a3 = 300;
                }
                a3++;
                if (a2 == 202) {//读取请求消息
                    sb.append("**请求参数：**");
                    sb.append("\n");
                    sb.append("\n");
                    sb.append("|" + lineTxt.replaceAll("AAAA", "|") + "|");
                    sb.append("\n");
                    sb.append("|:----    |:---|:----- |-----   |");
                    sb.append("\n");
                }

                if (a2 > 202 && a3 < 300) {//读取请求参数
                    if (lineTxt != null && lineTxt.length() > 3) {
                        sb.append("|" + lineTxt.replaceAll("AAAA", "|") + "|");
                        sb.append("\n");
                    } else {

                        sb.append("\n");
                        sb.append("**返回示例**");
                        sb.append("\n");
                        sb.append("```");
                        sb.append("\n");
                        sb.append("```");
                        sb.append("\n");
                        sb.append("**返回参数说明**");
                        sb.append("\n");

                    }

                }


                if (a3 == 302) {//读取请求消息
                    sb.append("\n");
                    sb.append("**响应参数：**");
                    sb.append("\n");
                    sb.append("\n");
                    sb.append("|" + lineTxt.replaceAll("AAAA", "|") + "|");
                    sb.append("\n");
                    sb.append("|:----    |:---|:----- |-----   |");
                    sb.append("\n");

                }


                if (a3 > 302) {//读取响应参数
                    if (lineTxt != null && lineTxt.length() > 3) {
                        lineTxt = lineTxt.substring(4, lineTxt.length() - 1);
                        sb.append("|" + lineTxt.replaceAll("AAAA", "|").replaceAll("AAA", "") + "|");
                        sb.append("\n");
                    }
                }


            }


            System.out.println();
            read.close();
        } else {
            System.out.println("找不到指定的文件");
        }
        sb.append("\n");
        sb.append("**备注** ");
        sb.append("\n");
        sb.append("\n");
        sb.append("- 更多返回错误代码请看首页的错误代码描述");

        System.out.println(sb.toString());
    }

}

