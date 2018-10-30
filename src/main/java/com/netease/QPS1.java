package com.netease;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QPS1 {
    public static void main(String args[]) throws IOException, ParseException {
        String filepath ="C:\\Users\\18638\\5MLog\\28data_grinder.log";
        File file = new File(filepath);
        File fs=new File("C:\\Users\\18638\\5MLog\\28data_grinder-1.log");
        FileOutputStream writerStream = new FileOutputStream(fs);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));

        //FileWriter bw = new FileWriter(fs);
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
        @SuppressWarnings("resource")
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis,"utf-8"),5*1024*1024);// 用5M的缓冲读取文本文件

        String line = "";
        List<String> readStr = new ArrayList<String>();
        while((line = reader.readLine()) != null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Pattern pattern_method = Pattern.compile("153535(.+?),");
            Matcher matcher_method = pattern_method.matcher(line);


            if(matcher_method.find()){
                // mailMap.put("date",line.substring(7,30));

                long lt = Long.parseLong(String.valueOf(matcher_method.group().substring(0,matcher_method.group().length()-1)));
                System.out.println(matcher_method.group().substring(0,matcher_method.group().length()-1));
                Date date = new Date(lt);
                String res = simpleDateFormat.format(date);
                readStr.add(res);
            }

        }
        System.out.println("\n计算所有对象出现的次数");//思路先使用set 去重，在用Collections工具类frequency方法统计元素出现频次
        Set uniqueSet = new HashSet(readStr);//hashSet去重
        for (Object temp : uniqueSet) {
            //System.out.println(temp + ": " + Collections.frequency(readStr, temp));//使用Collections工具类frequency方法统计元素出现频次

            writer.write(temp + "=" + Collections.frequency(readStr, temp));
            writer.write("\r\n");
        }
        //writer.write(String.valueOf(readStr));
        writer.close();

    }
}
