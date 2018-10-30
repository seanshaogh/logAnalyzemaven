package com.netease;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class QPS {
    public static void main(String args[]) throws IOException, ParseException {
        String filepath ="C:\\Users\\18638\\9MLog\\0907.txt";
        File file = new File(filepath);
        File fs=new File("C:\\Users\\18638\\9MLog\\0907-1.txt");
        FileOutputStream writerStream = new FileOutputStream(fs);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));

        //FileWriter bw = new FileWriter(fs);
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
        @SuppressWarnings("resource")
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis,"utf-8"),5*1024*1024);// 用5M的缓冲读取文本文件

        String line = "";
        List<String> readStr = new ArrayList<String>();
        while((line = reader.readLine()) != null){
            readStr.add(line.substring(0,13));
        }
        System.out.println("\n计算所有对象出现的次数");//思路先使用set 去重，在用Collections工具类frequency方法统计元素出现频次
        Set uniqueSet = new HashSet(readStr);//hashSet去重
        for (Object temp : uniqueSet) {
//            System.out.println(temp + ": " + Collections.frequency(readStr, temp));//使用Collections工具类frequency方法
            // 统计元素出现频次
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = Long.parseLong(String.valueOf(temp));
            Date date = new Date(lt);
            String res = simpleDateFormat.format(date);
            writer.write(res + "=" + Collections.frequency(readStr, temp));
            writer.write("\r\n");
        }
        //writer.write(String.valueOf(readStr));
        writer.close();

    }
}
