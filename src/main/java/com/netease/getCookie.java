package com.netease;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.*;

/**
 *
 * @ClassName: HttpLogin
 * @Description:  java通过httpclient获取cookie模拟登录
 * @author zeze
 * @date 2015年11月10日 下午4:18:08
 *
 */

public class getCookie {

    public static void main(String[] args) throws IOException {
        // 登陆 Url
        String loginUrl = "http://passport.mop.com/?targetUrl=http://hi.mop.com/?&g=1447141423230&loginCheck=UNLOGINED";
        // 需登陆后访问的 Url
//        String dataUrl = "http://hi.mop.com/?";
        HttpClient httpClient = new HttpClient();

        // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod = new PostMethod(loginUrl);

        // 设置登陆时要求的信息，用户名和密码
        String filepath ="C:\\Users\\18638\\5MLog\\1.txt";
        File file = new File(filepath);
        File fs=new File("C:\\Users\\18638\\5MLog\\1-2.txt");
        FileOutputStream writerStream = new FileOutputStream(fs);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));

        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
        @SuppressWarnings("resource")
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis,"utf-8"),5*1024*1024);// 用5M的缓冲读取文本文件
        String UserId="";
        while((UserId = reader.readLine()) != null){
            NameValuePair[] data = { new NameValuePair("loginName", UserId), new NameValuePair("loginPasswd", "") };
            postMethod.setRequestBody(data);
            try {
                // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
                httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
                int statusCode=httpClient.executeMethod(postMethod);

                // 获得登陆后的 Cookie
                Cookie[] cookies = httpClient.getState().getCookies();
                StringBuffer tmpcookies = new StringBuffer();
                for (Cookie c : cookies) {
                    tmpcookies.append(c.toString() + ";");
                    System.out.println("cookies = "+c.toString());
//                    Writer.write(c.toString());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

