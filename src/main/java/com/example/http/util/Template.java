package com.example.http.util;

/**
 * Created by WT on 2017/9/15.
 */
public class Template {
    public static String template(String url,String sendUrl,String arguments,String type,String result){
        StringBuilder builder=new StringBuilder();
        builder.append("**请求URL：** ");
        builder.append("<br/>");
        builder.append("- ` "+sendUrl+" `");
        builder.append("<br/><br/>");
        builder.append("**请求方式：**");
        builder.append("<br/>");
        builder.append("- "+type+" ");
        builder.append("<br/><br/>");
        builder.append("**参数：**");
        builder.append("<br/><br/>");
        builder.append("|参数名|必选|类型|说明|");
        builder.append("<br/>");
        builder.append("|:----    |:---|:----- |-----   |");
        builder.append("<br/>");
        String[] params=arguments.split("&");
        for (String param:params
                ) {
            String[] str=param.split("=");
            builder.append("|   ").append(str[0]).append("   |   ");
            builder.append("是").append("    |   ");
            builder.append("string").append("   |   ");
            builder.append("无").append("    |   ");
            builder.append("<br/>");
        }
        try {
            builder.append("<br/><br/>");
            builder.append("**返回示例**");
            builder.append("<br/><br/>");
            builder.append("<xmp>");
            builder.append(url);
            builder.append("\n\n");
            builder.append("</xmp>");
            builder.append("```");
            builder.append("<xmp>");
            builder.append("\n\n");
            builder.append(formatJson(result));
            builder.append("</xmp>");
            builder.append("<br/><br/>");
            builder.append("```");
            builder.append("<br/>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  builder.toString();
    }



    public static String template(String url,String sendUrl,String type,String result){
        StringBuilder builder=new StringBuilder();
        builder.append("**请求URL：** ");
        builder.append("<br/>");
        builder.append("- ` "+sendUrl+" `");
        builder.append("<br/><br/>");
        builder.append("**请求方式：**");
        builder.append("<br/>");
        builder.append("- "+type+" ");
        builder.append("<br/><br/>");
        try {
            builder.append("<br/><br/>");
            builder.append("**返回示例**");
            builder.append("<br/><br/>");
            builder.append("<xmp>");
            builder.append(url);
            builder.append("\n\n");
            builder.append("</xmp>");
            builder.append("```");
            builder.append("<xmp>");
            builder.append("\n\n");
            builder.append(formatJson(result));
            builder.append("</xmp>");
            builder.append("<br/><br/>");
            builder.append("```");
            builder.append("<br/>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  builder.toString();
    }


    /**
     * 格式化json
     * @param content
     * @return
     */
    public static String formatJson(String content) {
        StringBuffer sb = new StringBuffer();
        int index = 0;
        int count = 0;
        while(index < content.length()){
            char ch = content.charAt(index);
            if(ch == '{' || ch == '['){
                sb.append(ch);
                sb.append("\n");
                count++;
                for (int i = 0; i < count; i++) {
                    sb.append("\t");
                }
            }
            else if(ch == '}' || ch == ']'){
                sb.append("\n");
                count--;
                for (int i = 0; i < count; i++) {
                    sb.append("\t");
                }
                sb.append(ch);
            }
            else if(ch == ','){
                sb.append(ch);
                sb.append("\n");
                for (int i = 0; i < count; i++) {
                    sb.append("\t");
                }
            }
            else {
                sb.append(ch);
            }
            index ++;
        }
        return sb.toString();
    }
}
