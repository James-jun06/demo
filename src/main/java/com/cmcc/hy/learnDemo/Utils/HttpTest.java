package com.cmcc.hy.learnDemo.Utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Project:  learnDemo
 * File Created by James on 2018/3/8
 *
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 */
public class HttpTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String baseUrl = "http://218.205.115.242:11080/littleapp/open/user/getdeptuserlist";
        //String baseUrl1 = "http://172.23.24.106:18088/littleapp/open/user/getdeptuserlist";
        //String baseUrl = "http://112.33.16.124:8081/littleapp/open/user/getdeptuserlist";
        String body = createPostbody();
        String sign = createSign(body);
        System.out.println("body="+body+" sign="+sign);
        String result  = httpRequest(baseUrl+"?sign="+sign, body);
        System.out.println("result="+result);
    }
    public static String httpRequest(String url , String params){
        BufferedReader reader = null;
        OutputStream out = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            URL qurl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) qurl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(6000);
            connection.setReadTimeout(300000);
            connection.setRequestProperty("Content-Type", "application/json");
            if(params != null){
                //传参
                out = connection.getOutputStream();
                out.write(params.getBytes());
                out.flush(); //清空缓冲区,发送数据
                out.close();
            }
            connection.connect();
            InputStream is = connection.getInputStream();
            System.out.println("code="+connection.getResponseCode());
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strReader = null;
            while ((strReader = reader.readLine()) != null) {
                sbf.append(strReader);
            }
            reader.close();
            result = sbf.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String createPostbody(){
        String timestamp =new Date().getTime()+"";
        JSONObject argss = new JSONObject();
        argss.put("dept_id", "2125170497167362");  //2125170497167362 2289086261024768
        argss.put("appkey", "yqx_open_zq_kaoqin");
        argss.put("corp_id", "1730217856127852");  //yqx275cd2a13448b9d835c2f4657eb6378f
        argss.put("timestamp", timestamp);
        argss.put("page", "1");
        argss.put("rows", "300");
        return argss.toJSONString();
    }
    public static String createSign(String postbody) {
        String MYSIGN = "$" + postbody + "$" + "yqx_1gfBt+Cp5YlswSAzTGXdvOnd/Hg=" + "$" + "ci09oc-56g78wb5h1b_yzg5w77g_6hr1algd_7ccnwg35lc38";
        MYSIGN = RandomUtil.MD5(MYSIGN);
        return MYSIGN;

    }
}
