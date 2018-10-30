package com.netease;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

public class logAnalyze {
    public static void main(String args[]) throws IOException, ParseException {
        String filepath ="C:\\Users\\18638\\9MLog\\bigdata-webserver.log.2018-09-09";
        File file = new File(filepath);
        File fs=new File("C:\\Users\\18638\\9MLog\\0909.txt");
        FileOutputStream writerStream = new FileOutputStream(fs);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));

        //FileWriter bw = new FileWriter(fs);
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
        @SuppressWarnings("resource")
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis,"utf-8"),5*1024*1024);// 用5M的缓冲读取文本文件

        String line = "";
        while((line = reader.readLine()) != null){
            //TODO: write your business

            if(line.contains("method = ")&&line.contains("cost_time=")){
                //String test = "2017-07-03 00:01:16,823  INFO TaCheckController: api=match, param={\"account\":\"36206484\",\"checkOnly\":true,\"checkTypes\":[\"CLUSTER\"],\"content\":\"嗯\"}";
                //Pattern pattern_date = Pattern.compile("");
                Pattern pattern_method = Pattern.compile("method = (.+?),");//匹配的模式
                Pattern pattern_costTime = Pattern.compile("cost_time=(\\d*)");
                //通配符中也要加入转移字符 (.+?)代表要查找的内容
                //Matcher matcher_date = pattern_date.matcher(line);
                Matcher matcher_method = pattern_method.matcher(line);
                Matcher matcher_costTime = pattern_costTime.matcher(line);
                JsonObject obj = new JsonObject();
                if(matcher_method.find()&&matcher_costTime.find()){
                    obj.addProperty("date",line.substring(7,30));
                    obj.addProperty("method",matcher_method.group());
                    obj.addProperty("costTime",matcher_costTime.group());


                }
                String dateStr= String.valueOf((obj.get("date")));
                String methodStr = String.valueOf(obj.get("method"));
                String costTimeStr = String.valueOf(obj.get("costTime"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = dateFormat.parse(dateStr.substring(1,dateStr.length()-1));
                long ts = date.getTime();
                String strInfo = String.valueOf(ts)+methodStr.substring(10,methodStr.length()-2)+
                        costTimeStr.substring(10,costTimeStr.length()-1);

                writer.write(strInfo);
                writer.write("\r\n");

            }
        }
        writer.close();
    }
}
