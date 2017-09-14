package com.example.http.util;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WT on 2017/9/14.
 */
public class jiexi {
    public static Path startingDir = Paths.get("C:\\Users\\WT\\Desktop\\word\\");
    public static String templatePath = "F:\\template\\template.txt";

    private static class FindJavaVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (file.toString().endsWith(".txt")) {
//                System.out.println(file.getFileName());
                String template=readTemplate(templatePath);
//                System.out.println(template);
                String sql=readFile(file);
                analysis(sql,template);
                System.out.println();
                System.out.println();
            }
            return FileVisitResult.CONTINUE;
        }
    }

    public static String readTemplate(String file){
        StringBuilder builder=new StringBuilder();
        try {
            File catalinaFile = new File(file);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(catalinaFile),"UTF-8"); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            while (line != null) {
                builder.append(line+"\n");
                line = br.readLine(); // 一次读入一行数据
                if (line == null) {
                    break;
                }
            }
            br.close();
//          System.out.println(builder.toString());
        }catch (IOException e){
            e.fillInStackTrace();
        }
        return builder.toString();
    }

    public static String readFile(Path file){
        StringBuilder builder=new StringBuilder();
        try {
            StringBuilder word=new StringBuilder();
            File catalinaFile = new File(file.toUri());
            InputStreamReader reader = new InputStreamReader(new FileInputStream(catalinaFile),"UTF-8"); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            while (line != null) {
                if ("".equals(line)) {
                    builder.append("AAAAAAAAAAA");
                }else{
                    line=line.replace("\t","|");
                    builder.append("|"+line+"|"+"\n");
                }
                line = br.readLine(); // 一次读入一行数据
                if (line == null) {
                    break;
                }
            }
            br.close();
        }catch (IOException e){
            e.fillInStackTrace();
        }
        return builder.toString();
    }


    public static void analysis(String sql,String template){
        String[] params=sql.split("AAAAAAAAAAA");
        template=template.replace("AAAAAAAAAA",params[0]);
        template=template.replace("BBBBBBBBBB",params[1]);
        System.out.println(template);
    }

    public static void main(String[] args) throws IOException {
        Files.walkFileTree(startingDir,new FindJavaVisitor());
    }

}
