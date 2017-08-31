package com.giftpay.pay.utils;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * httpClient请求工具
 *
 *
 */
public class HttpUtil {

	public final static String UTF8="UTF-8";
	
	/**
	 * 发送get请求
	 * @param url
	 * @param encode
	 * @return
	 */
	public static String sendGet(String url, String encode) {

		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(300000).setConnectTimeout(300000).build();//设置请求和传输超时时�?
		get.setConfig(requestConfig);
		get.setHeader("Content-Type", "text/html;charset=UTF-8");
		try {
			HttpResponse httpResponse = closeableHttpClient.execute(get);
			HttpEntity responceEntity = httpResponse.getEntity();
			System.out.println("status:" + httpResponse.getStatusLine());
			return EntityUtils.toString(responceEntity, encode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				closeableHttpClient.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}


	/**
	 * 发送post请求
	 * @param url
	 * @param encode
	 * @param xmlParams
	 * @return
	 */
	public static String sendPost(String url, String encode, String xmlParams) {

		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "text/html;charset=UTF-8");
		try {
			if (xmlParams != null) {
				StringEntity stringEntity = new StringEntity(xmlParams, encode);
				System.out.println("sb=" + xmlParams);
				post.setEntity(stringEntity);
			}
			post.addHeader("Content-Type", "text/xml");
			HttpResponse httpResponse = closeableHttpClient.execute(post);
			HttpEntity responceEntity = httpResponse.getEntity();
			System.out.println("status:" + httpResponse.getStatusLine());
			return EntityUtils.toString(responceEntity, encode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				closeableHttpClient.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {

	}
}
