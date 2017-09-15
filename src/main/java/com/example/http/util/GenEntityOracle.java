package com.example.http.util;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Selegant on 2017/9/11.
 */


public class GenEntityOracle {

    public static Path startingDir = Paths.get("C:\\Users\\WT\\Desktop\\湖州测试库SQL\\");

    private static class FindJavaVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (file.toString().endsWith(".txt")) {
                System.out.println(file.getFileName());
                String sql=readFile(file);
                analysis(sql);
                System.out.println();
                System.out.println();
            }
            return FileVisitResult.CONTINUE;
        }
    }


    public static String readFile(Path file){
        StringBuilder builder=new StringBuilder();
        try {
            File catalinaFile = new File(file.toUri());
            InputStreamReader reader = new InputStreamReader(new FileInputStream(catalinaFile),"UTF-8"); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            while (line != null) {
                builder.append(line.trim());
                line = br.readLine(); // 一次读入一行数据
                if (line == null) {
                    break;
                }
            }
            br.close();
//            System.out.println(builder.toString());
        }catch (IOException e){
            e.fillInStackTrace();
        }
        return builder.toString();
    }

    public static void getColumn(String columnStr,Map<String,String> map){
        String[] columns=columnStr.split(",");
        for (String column :columns
             ) {
//          System.out.println(column);
            StringBuilder builder=new StringBuilder();
            if(column.contains("PRIMARY")){
                continue;
            }
            String[] attribute=column.split(" ");
            String columnName=attribute[0];
            String columnType=attribute[1];
            builder.append("|").append(columnName).append("|").append(columnType).append("|");
            if(column.contains("NOT NULL")){
                builder.append("否   ").append("|");
            }else{
                builder.append("是   ").append("|");
            }
            if(column.contains("DEFAULT")){
                int start=column.indexOf("'");
                int end=column.lastIndexOf("'");
                String str=column.substring(start+1,end);
                builder.append(str+"    ").append("|");
            }else{
                builder.append("    ").append("|");
            }
            String comment=map.get(columnName);
            if(comment!=null&&!comment.isEmpty()){
                builder.append(comment).append("|");
            }else {
                if("ID".equals(columnName)){
                    builder.append("主键  ").append("|");
                }else{
                    builder.append("    ").append("|");
                }
            }
            System.out.println(builder.toString());
        }
    }

    public static Map<String,String> comment(String comment) {
        String[] comments = comment.split(";C");
        Map<String,String> map=new HashMap<>();
        for (String str : comments
                ) {
            if (str.contains("TABLE")&&!str.contains("COLUMN")) {
                continue;
            }
            int start = str.indexOf(".");
            int end = str.lastIndexOf("'");
            str = str.substring(start+1,end);
            String[] strs = str.split("IS'");
            String key = strs[0];
            String value = strs[1];
            map.put(key,value);
        }
        return map;
    }

    public static void analysis(String sql){
        StringBuilder builder=new StringBuilder(sql);
        int start=builder.indexOf("(");
        int end=builder.lastIndexOf(");");
        String columnStr=builder.substring(start+1,end);
        start=builder.indexOf(";");
        String comment=builder.substring(start+1,builder.length());
//        System.out.println(comment);
        Map<String,String> map=comment(comment);
        getColumn(columnStr,map);
    }

    public static void main(String[] args) throws IOException {
        Files.walkFileTree(startingDir,new FindJavaVisitor());
//      String sql=readFile();
//      analysis(sql);
//      Map<String,String> map=comment("COMMENT ON TABLE BASE_BANNERIS'轮播图';COMMENT ON COLUMN BASE_BANNER.BANNER_TITLEIS'轮播图标题';COMMENT ON COLUMN BASE_BANNER.PIC_URLIS'图片地址';");
//      System.out.println(map.toString());
    }
}