package com.example.http.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by WT on 2017/9/14.
 */
public class QzApi {
    public static  String filePath="F:\\word\\test1.txt";

    public static void log(File file,int n) throws IOException {

        StringBuilder builder=new StringBuilder("F:\\result\\");
        builder.append("Q").append(n).append(".txt");
        File logFile = new File(builder.toString());

        BufferedWriter writer=new BufferedWriter(new FileWriter(logFile));
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file)); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        String line = "";
        line = br.readLine();
        int i=1;
        writer.write("**简要描述：**");
        writer.write("\n\n");
        writer.write(line);
        writer.write("\n\n");
        writer.write("**请求URL：**");
        writer.write("\n\n");
        while (line != null) {
            i++;
//            writer.write(line);
//            writer.newLine();
            line = br.readLine(); // 一次读入一行数据
            if (line == null) {
                break;
            }
            if(i==2){
                writer.write(line);
                writer.write("\n\n");
                writer.write("**请求方式：**\n- POST\n");
                writer.write("\n\n");
                writer.write("**请求参数：**");
                writer.write("\n\n");
                writer.write("|字段名|字段标示|类型|可否为空|备注|");
                writer.write("\n");
                writer.write("|:----    |:---|:----- |-----   |\n");
                continue;
            }
            if(line.contains("输出")){
                writer.write("\n\n");
                writer.write("**响应参数：**");
                writer.write("\n\n");
                writer.write("|字段名|字段标示|类型|可否为空|备注|");
                writer.write("\n");
                writer.write("|:----    |:---|:----- |-----   |\n");
            }else{
                String[] strings=line.split("\t");

                StringBuilder bu=new StringBuilder();
                if(strings[1]!=null&&!strings[1].isEmpty()){
                    bu.append("|").append(strings[1]);
                }else {
                    bu.append("|").append("\t").append("|");
                }
                if(strings[2]!=null&&!strings[2].isEmpty()){
                    bu.append("|").append(strings[2]);
                }else {
                    bu.append("|").append("\t").append("|");
                }
                if(strings[3]!=null&&!strings[3].isEmpty()){
                    bu.append("|").append(strings[3]).append("|");
                }else {
                    bu.append("|").append("\t").append("|");
                }
                bu.append("否").append("|").append("\t").append("|");
                writer.write(bu.toString());
                writer.write("\n");
            }

        }
        writer.write("\n");
        writer.write(" **备注**");
        writer.flush(); // 把缓存区内容压入文件
        writer.close(); // 最后记得关闭文件
    }

    public static void main(String[] args) throws IOException {
        File file=new File(filePath);
        log(file,0);
    }
}
