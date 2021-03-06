package com.thinkgem.jeesite.common;

import com.thinkgem.jeesite.common.config.Global;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class OrderStatic {
    //向供应商分发订单，由平台提供
    public static final String  SendOrder= Global.getConfig("lxd.url")+"/api/Interface/SendOrder";
   //供应商发货后向平台通知发货状态，由平台提供
    public static final String  SendGoods=Global.getConfig("lxd.url")+"/api/Interface/SendGoods";
    //获取平台商品的占用数量
    public static final String  GetItemCodeOccupyStock=Global.getConfig("lxd.url")+"/api/Interface/GetItemCodeOccupyStock";
    //sap1、凭单接口  接口地址：http://172.17.250.191:9091/v1/journal
    public static final String journal=Global.getConfig("sap.url")+"/v1/journal";
    //销售订单接口  接口地址：http://172.17.250.191:9091/v1/salesorder
    public static final String salesorder=Global.getConfig("sap.url")+"/v1/salesorder";
    //销售交货接口  接口地址：http://172.17.250.191:9091/v1/salesdelivery
    public static final String salesdelivery=Global.getConfig("sap.url")+"/v1/salesdelivery";

    //销售交货接口  接口地址：http://172.17.250.191:9091/v1/stock
    public static final String salesstock=Global.getConfig("sap.url")+"/v1/stock?itemCode=";

    public static String md5(String plainText){
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }

        return md5code;
    }
    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static String get(String url) {
        String str="";
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost，
        HttpGet httppost = new HttpGet(url);
        httppost.addHeader("Content-Type", "application/json");

        // 创建参数队列
        try {
            //httppost.setEntity(new StringEntity(params, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    str= EntityUtils.toString(entity, "UTF-8");

                }
            } finally {
                response.close();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static String post(String url, String params) {
        String str="";
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost，
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "application/json");

        // 创建参数队列
        try {
            httppost.setEntity(new StringEntity(params, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    str= EntityUtils.toString(entity, "UTF-8");

                }
            } finally {
                response.close();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static String lxdpost(String url,String params) {

        Map<String, String> headers=new HashMap();
        headers.put("From", Global.getConfig("lxd.From"));
        //随机数+空格+MD5（MD5（KEY）+随机数）
        int r=new Random().nextInt();
        String keys=Global.getConfig("lxd.Key");
        String key=r+" "+ md5(md5(keys).toUpperCase()+r).toUpperCase();
        headers.put("Authorization",key);
        // System.out.println(headers.toString());
        return post(url,params,headers);
    }
    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static String lxdpost(String url,Map<String, String> params) {

        Map<String, String> headers=new HashMap();
        headers.put("From", Global.getConfig("lxd.From"));
        //随机数+空格+MD5（MD5（KEY）+随机数）
        int r=new Random().nextInt();
        String keys=Global.getConfig("lxd.Key");
        String key=r+" "+ md5(md5(keys).toUpperCase()+r).toUpperCase();
        headers.put("Authorization",key);
       // System.out.println(headers.toString());
        return post(url,params,headers);
    }
    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static String lxdpostJson(String url,String jsonParam) {

        Map<String, String> headers=new HashMap();
        headers.put("From", Global.getConfig("lxd.From"));
        //随机数+空格+MD5（MD5（KEY）+随机数）
        int r=new Random().nextInt();
        String keys=Global.getConfig("lxd.Key");
        String key=r+" "+ md5(md5(keys).toUpperCase()+r).toUpperCase();
        headers.put("Authorization",key);
        // System.out.println(headers.toString());
        return doPostJson(url,headers,jsonParam);
    }
    /**
     * POST请求（带json参数）
     * @param url					请求地址
     * @param headers		响应超时时间，根据业务而定，单位毫秒;
     * 							如果访问一个接口,多少时间内无法返回数据,就直接放弃此次调用
     * @param jsonParam	json参数
     * @return
     */
    public static String doPostJson(String url, Map<String, String> headers, String jsonParam) {
        String str="";
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost，
        HttpPost httppost = new HttpPost(url);
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            httppost.setHeader(key, headers.get(key).toString());
            System.out.println(key+"="+ headers.get(key).toString());
        }
        try {
            StringEntity stringEntity = new StringEntity(jsonParam, ContentType.APPLICATION_JSON);
            httppost.setEntity(stringEntity);
            //System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    str= EntityUtils.toString(entity, "UTF-8");
                    /*System.out.println("--------------------------------------");
                    System.out.println("Response content: " + str);
                    System.out.println("--------------------------------------");*/
                }
            } finally {
                response.close();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static String post(String url,String params,Map<String, String> headers) {
        String str="";
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost，
        HttpPost httppost = new HttpPost(url);
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            httppost.setHeader(key, headers.get(key).toString());
            System.out.println(key+"="+ headers.get(key).toString());
        }
        UrlEncodedFormEntity uefEntity;
        System.out.println( "参数"+ params);
        try {
            StringEntity stringEntity = new StringEntity(params, ContentType.APPLICATION_JSON);
            httppost.setEntity(stringEntity);
            //System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    str= EntityUtils.toString(entity, "UTF-8");
                    /*System.out.println("--------------------------------------");
                    System.out.println("Response content: " + str);
                    System.out.println("--------------------------------------");*/
                }
            } finally {
                response.close();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static String post(String url,Map<String, String> params,Map<String, String> headers) {
        String str="";
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost，
        HttpPost httppost = new HttpPost(url);
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            httppost.setHeader(key, headers.get(key).toString());
            System.out.println(key+"="+ headers.get(key).toString());
        }
        // 创建参数队列
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            formparams.add(new BasicNameValuePair(key.toString(), params.get(key.toString())));
             System.out.println(key.toString()+"="+params.get(key.toString()));
        }


        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            //System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    str= EntityUtils.toString(entity, "UTF-8");
                    /*System.out.println("--------------------------------------");
                    System.out.println("Response content: " + str);
                    System.out.println("--------------------------------------");*/
                }
            } finally {
                response.close();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
}
