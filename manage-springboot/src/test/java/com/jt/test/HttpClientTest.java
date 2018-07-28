package com.jt.test;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientTest {

	/**
	 * 测试利用HttpClient客户端获取淘宝数据
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Test
	public void test() throws ClientProtocolException, IOException{
		//创建连接对象
		CloseableHttpClient client = HttpClients.createDefault();
		//创建需要访问的url
		String url = "https://www.taobao.com";
		//调用get请求方式
		HttpGet get = new HttpGet(url);
		//底层的发起请求
		CloseableHttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		String body = EntityUtils.toString(entity);
		System.out.println(body);
	}
}





























