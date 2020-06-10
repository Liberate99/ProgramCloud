package com.yunjuanyunshu.modules.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 利用okhttp进行get和post的访问
 * @author wwyz
 */
public class OKHttpUtil {
    public final static int CONNECT_TIMEOUT = 60;
    public final static int READ_TIMEOUT = 100;
    public final static int WRITE_TIMEOUT = 60;
    public static final String host="http://localhost";

    /**
     * 发起get请求
     *
     * @param url
     * @return
     */
    public static String httpGet(String url) {
        url=host+url;
        String result = null;
        //todo 完善单例模式，写个连接池
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送httppost请求
     *
     * @param url
     * @param data  提交的参数为key=value&key1=value1的形式
     * @return
     */
    public static String httpPost(String url, String data) {
        url=host+url;
        String result = null;
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/html;charset=utf-8"), data);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String httpPost(String url, JsonObject data) {
        url=host+url;
        String result = null;
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), data.toString());
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args ){
//        System.out.println(httpGet("/auth/email/qq%40qq.com"));
//        System.out.println(httpGet("/auth/name/75011"));
        String userId="5a756fa39d410548001f44bf";
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("slug","cheng1");
        jsonObject.addProperty("name","ChenG1");
        jsonObject.addProperty("email","qqq@weqwe.asd");
        jsonObject.addProperty("_id",userId);
        jsonObject.addProperty("createdOnHost","localhost");
        jsonObject.addProperty("preferredLanguage","zh-HANS");
        jsonObject.addProperty("testGroupNumber",200);
        jsonObject.addProperty("createdOnHost","localhost");
        jsonObject.addProperty("anonymous",false);
        jsonObject.addProperty("__v",0);
        jsonObject.addProperty("referrer","http://localhost:3000/home");
        jsonObject.addProperty("firstName","q");
        jsonObject.addProperty("lastName","qqqq");
        jsonObject.addProperty("dateCreated","2018-02-04T08:36:34.406Z");
        JsonObject email=new JsonObject();
        JsonObject generalNews=new JsonObject();
        generalNews.addProperty("enabled",true);
        email.add("generalNews",generalNews);
        jsonObject.add("emails",email);
        System.out.println(jsonObject.toString());
        System.out.println(httpPost("/db/user/"+userId+"/signup-with-password",jsonObject));
    }
}