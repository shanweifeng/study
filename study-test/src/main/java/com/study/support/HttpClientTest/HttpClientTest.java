package com.study.support.HttpClientTest;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

/**
 * @Author shanweifeng
 * @Description:  HttpClient
 * @Date: Created in 10:32 2018/4/8
 * @Modified By:
 */
public class HttpClientTest {

    public static void main(String[] agrs){
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod("http://www.apache.org");
        try {
            client.executeMethod(method);
            byte[] responseBody = method.getResponseBody();
            String returnData = new String(responseBody,"utf-8");
            System.out.println(returnData);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            method.releaseConnection();
        }
    }
}
