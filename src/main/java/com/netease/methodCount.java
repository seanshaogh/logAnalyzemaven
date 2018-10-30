package com.netease;

import org.apache.commons.collections.map.MultiValueMap;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class methodCount {
    public static void main(String args[]) throws IOException, ParseException {
        String filepath ="C:\\Users\\18638\\9MLog\\bigdata-webserver.log.2018-09-07";
        File file = new File(filepath);
        File fs=new File("C:\\Users\\18638\\9MLog\\0907-count.txt");
        FileOutputStream writerStream = new FileOutputStream(fs);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));

        //FileWriter bw = new FileWriter(fs);
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
        @SuppressWarnings("resource")
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis,"utf-8"),5*1024*1024);// 用5M的缓冲读取文本文件

        String line = "";
        MultiValueMap mailMap = new MultiValueMap();//实现1个key对应多个value,可以用apach提供的MultiValueMap
        List<String> methodStr = new ArrayList<String>();
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

                if(matcher_method.find()&&matcher_costTime.find()){
                    // mailMap.put("date",line.substring(7,30));
                    methodStr.add(matcher_method.group().substring(9,matcher_method.group().length()-1));//提前MultiValueMap的key值

                }
                //System.out.println(matcher_method.group().substring(9,matcher_method.group().length()-1));
                //System.out.println(matcher_costTime.group().substring(10,matcher_costTime.group().length()));
            }
        }
        Set uniqueMethod = new HashSet(methodStr);

        for(Object tmp:uniqueMethod){
            writer.write(tmp+":"+Collections.frequency(methodStr,tmp));
            writer.write("\r\n");

        }
        writer.close();
    }
}
