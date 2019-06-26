package com.eyaoshun.activiti.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

public class ActivitiHttps {

	// 编码格式。发送编码格式统一用UTF-8
	private static final String ENCODING = "UTF-8";

	// 设置连接超时时间，单位毫秒。
	private static final int CONNECT_TIMEOUT = 60000;

	// 请求获取数据的超时时间(即响应时间)，单位毫秒。
	private static final int SOCKET_TIMEOUT = 60000;

	/**
	 * 发送get请求；不带请求头和请求参数
	 * 
	 * @param url
	 *            请求地址
	 * @return
	 * @throws Exception
	 */
	public static Object doGet(String url) throws Exception {
		return doGet(url, null, null);
	}

	/**
	 * 发送get请求；带请求参数
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数集合
	 * @return
	 * @throws Exception
	 */
	public static Object doGet(String url, Map<String, String> params) throws Exception {
		return doGet(url, null, params);
	}

	/**
	 * 发送get请求；带请求头和请求参数
	 * 
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求头集合
	 * @param params
	 *            请求参数集合
	 * @return
	 * @throws Exception
	 */
	public static Object doGet(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
		// 创建httpClient对象
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建带有认证的客户端
		CloseableHttpClient httpClient = getCredentialHttpClient();

		// 创建访问的地址
		URIBuilder uriBuilder = new URIBuilder(url);
		if (params != null) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				uriBuilder.setParameter(entry.getKey(), entry.getValue());
			}
		}

		// 创建http对象
		HttpGet httpGet = new HttpGet(uriBuilder.build());
		/**
		 * setConnectTimeout：设置连接超时时间，单位毫秒。 setConnectionRequestTimeout：设置从connect
		 * Manager(连接池)获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
		 * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
		 */
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		httpGet.setConfig(requestConfig);

		// 设置请求头
		packageHeader(headers, httpGet);

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, httpClient, httpGet);
		} finally {
			// 释放资源
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 发送post请求；不带请求头和请求参数
	 * 
	 * @param url
	 *            请求地址
	 * @return
	 * @throws Exception
	 */
	public static Object doPost(String url) throws Exception {
		return doPost(url, null, null);
	}

	/**
	 * 发送post请求；带请求参数
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数集合
	 * @return
	 * @throws Exception
	 */
	public static Object doPost(String url, Map<String, String> params) throws Exception {
		return doPost(url, null, params);
	}

	/**
	 * 发送post请求；带请求头和请求参数
	 * 
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求头集合
	 * @param params
	 *            请求参数集合
	 * @return
	 * @throws Exception
	 */
	public static Object doPost(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
		// 创建httpClient对象
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpClient httpClient = getCredentialHttpClient();

		// 创建http对象
		HttpPost httpPost = new HttpPost(url);
		/**
		 * setConnectTimeout：设置连接超时时间，单位毫秒。 setConnectionRequestTimeout：设置从connect
		 * Manager(连接池)获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
		 * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
		 */
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPost.setConfig(requestConfig);
		// 设置请求头
		/*
		 * httpPost.setHeader("Cookie", ""); httpPost.setHeader("Connection",
		 * "keep-alive"); httpPost.setHeader("Accept", "application/json");
		 * httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		 * httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
		 * httpPost.setHeader("User-Agent",
		 * "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36"
		 * );
		 */
		packageHeader(headers, httpPost);

		// 封装请求参数
		packageParam(params, httpPost);

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, httpClient, httpPost);
		} finally {
			// 释放资源
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 发送put请求；不带请求参数
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数集合
	 * @return
	 * @throws Exception
	 */
	public static Object doPut(String url) throws Exception {
		return doPut(url, null);
	}

	/**
	 * 发送put请求；带请求参数
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数集合
	 * @return
	 * @throws Exception
	 */
	public static Object doPut(String url, Map<String, String> params) throws Exception {
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpClient httpClient = getCredentialHttpClient();

		HttpPut httpPut = new HttpPut(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPut.setConfig(requestConfig);

		packageParam(params, httpPut);

		CloseableHttpResponse httpResponse = null;

		try {
			return getHttpClientResult(httpResponse, httpClient, httpPut);
		} finally {
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 发送delete请求；不带请求参数
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数集合
	 * @return
	 * @throws Exception
	 */
	public static Object doDelete(String url) throws Exception {
		// CloseableHttpClient httpClient = HttpClients.createDefault();

		CloseableHttpClient httpClient = getCredentialHttpClient();
		HttpDelete httpDelete = new HttpDelete(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		httpDelete.setConfig(requestConfig);

		CloseableHttpResponse httpResponse = null;
		try {
			return getHttpClientResult(httpResponse, httpClient, httpDelete);
		} finally {
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 发送delete请求；带请求参数
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数集合
	 * @return
	 * @throws Exception
	 */
	public static Object doDelete(String url, Map<String, String> params) throws Exception {
		if (params == null) {
			params = new HashMap<String, String>();
		}

		params.put("_method", "delete");
		return doPost(url, params);
	}

	/**
	 * Description: 封装请求头
	 * 
	 * @param params
	 * @param httpMethod
	 */
	public static void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
		// 封装请求头
		if (params != null) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				// 设置到请求头到HttpRequestBase对象中
				httpMethod.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * Description: 封装请求参数
	 * 
	 * @param params
	 * @param httpMethod
	 * @throws UnsupportedEncodingException
	 */
	public static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
			throws UnsupportedEncodingException {
		// 封装请求参数
		if (params != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}

			// 设置到请求的http对象中
			httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
		}
	}

	/**
	 * Description: 获得响应结果
	 * 
	 * @param httpResponse
	 * @param httpClient
	 * @param httpMethod
	 * @return
	 * @throws Exception
	 */
	public static Object getHttpClientResult(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient,
			HttpRequestBase httpMethod) throws Exception {
		// 执行请求
		httpResponse = httpClient.execute(httpMethod);

		// 获取返回结果
		if (httpResponse != null && httpResponse.getStatusLine() != null) {
			String content = "";
			if (httpResponse.getEntity() != null) {
				content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
			}
			return JSON.parse(content);
		}
		return StringUtils.EMPTY;
	}

	public static InputStream getHttpInputStream(String url, Map<String, String> params, Map<String, String> headers) throws ClientProtocolException, IOException, URISyntaxException {
		// 创建带有认证的客户端
		CloseableHttpClient httpClient = getCredentialHttpClient();

		// 创建访问的地址
		URIBuilder uriBuilder = new URIBuilder(url);
		if (params != null) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				uriBuilder.setParameter(entry.getKey(), entry.getValue());
			}
		}

		// 创建http对象
		HttpGet httpGet = new HttpGet(uriBuilder.build());
		/**
		 * setConnectTimeout：设置连接超时时间，单位毫秒。 setConnectionRequestTimeout：设置从connect
		 * Manager(连接池)获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
		 * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
		 */
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		httpGet.setConfig(requestConfig);

		// 设置请求头
		packageHeader(headers, httpGet);

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求
			httpResponse = httpClient.execute(httpGet);

			// 获取返回结果
			if (httpResponse != null && httpResponse.getStatusLine() != null) {
				if (httpResponse.getEntity() != null) {
					InputStream content = httpResponse.getEntity().getContent();
					// byte[] content = EntityUtils.toByteArray(httpResponse.getEntity());
					return content;
				}
			}
			return null;
		} finally {
			// 释放资源
			// release(httpResponse, httpClient);
		}
	}
	
	/**
	 * activiti Post 发送
	 * @param url
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static Object postJson(String url, String jsonStr) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		return activitiHttpRest(httpPost, jsonStr);
	}
	
	/**
	 * activiti Put 发送
	 * @param url
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static Object putJson(String url, String jsonStr) throws Exception {
		HttpPut httpPut = new HttpPut(url);
		return activitiHttpRest(httpPut, jsonStr);
	}
	
	private static Object activitiHttpRest(HttpEntityEnclosingRequestBase http, String jsonStr) throws Exception {
		CloseableHttpClient httpClient = getCredentialHttpClient();

		// 创建http对象
		/**
		 * setConnectTimeout：设置连接超时时间，单位毫秒。 setConnectionRequestTimeout：设置从connect
		 * Manager(连接池)获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
		 * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
		 */
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		http.setConfig(requestConfig);
		// 设置请求头
		http.setHeader("Content-Type", "application/json");

		http.setEntity(new StringEntity(jsonStr, ENCODING));

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, httpClient, http);
		} finally {
			// 释放资源
			release(httpResponse, httpClient);
		}
	}
	
	/**
	 * post 发送文件的请求
	 * @param url
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Object postMultipartEntity(String url, MultipartFile file) throws Exception {
		CloseableHttpClient httpClient = getCredentialHttpClient();

		// 创建http对象
		HttpPost httpPost = new HttpPost(url);
		
		
		ByteArrayBody binFileBody = new ByteArrayBody(file.getBytes(), ContentType.APPLICATION_JSON, file.getOriginalFilename());

		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
				.create();
		// add the file params
		multipartEntityBuilder.addPart("file", binFileBody);

		HttpEntity reqEntity = multipartEntityBuilder.build();
		httpPost.setEntity(reqEntity);
		

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, httpClient, httpPost);
		} finally {
			// 释放资源
			release(httpResponse, httpClient);
		}
	}

	/**
	 * Description: 释放资源
	 * 
	 * @param httpResponse
	 * @param httpClient
	 * @throws IOException
	 */
	public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
		// 释放资源
		if (httpResponse != null) {
			httpResponse.close();
		}
		if (httpClient != null) {
			httpClient.close();
		}
	}

	/**
	 * 获取带有认证的HttpClient
	 * 
	 * @return
	 */
	private static CloseableHttpClient getCredentialHttpClient() {
		BasicCredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("kermit", "kermit");
		provider.setCredentials(AuthScope.ANY, credentials);

		CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
		return client;
	}
}