/**
 * 
 */
package com.light.sword.ylazy.engine;

import static java.lang.System.out;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.util.StringUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author jack http执行引擎
 */
public class OkHttp {

	public static String run(String url, String method, String json) {
		if (StringUtils.isEmpty(url))
			return "url cannot be empty";

		if (StringUtils.isEmpty(method))
			return "method cannot be empty";

		String result = null;
		if ("GET".equalsIgnoreCase(method)) {
			result = get(url);
		} else if ("POST".equalsIgnoreCase(method)) {
			result = postJson(url, json);
		} else {
			result = "method should be GET or POST";
		}
		return result;
	}

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	/**
	 * 支持https http请求执行引擎
	 * 
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		InputStream inStream = null;
		OutputStream outStream = null;
		String result = "";
		try {
			URL console = new URL(new String(url.getBytes("utf-8")));

			HttpURLConnection conn = (HttpURLConnection) console.openConnection();
			// 如果是https
			if (conn instanceof HttpsURLConnection) {
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
				((HttpsURLConnection) conn).setSSLSocketFactory(sc.getSocketFactory());
				((HttpsURLConnection) conn).setHostnameVerifier(new TrustAnyHostnameVerifier());
			}
			conn.connect();
			InputStream is = conn.getInputStream();
			DataInputStream indata = new DataInputStream(is);
			String ret = "";

			while (ret != null) {
				ret = indata.readLine();
				if (ret != null && !ret.trim().equals("")) {
					result = result + new String(ret.getBytes("ISO-8859-1"), "utf-8");
				}
			}
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		} finally {
			try {
				inStream.close();
			} catch (Exception e) {
			}
			try {
				outStream.close();
			} catch (Exception e) {
			}
		}

		out.println("请求url:" + url);
		out.println("响应结果:" + result);

		return result;
	}

	/**
	 * post json
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	public static String postJson(String url, String json) {
		String result = null;
		// 创建一个OkHttpClient对象
		OkHttpClient okHttpClient = new OkHttpClient();
		// 创建一个RequestBody(参数1：数据类型 参数2传递的json串)
		RequestBody requestBody = RequestBody.create(JSON, json);
		// 创建一个请求对象
		Request request = new Request.Builder().url(url).post(requestBody).build();
		// 发送请求获取响应
		try {
			Response response = okHttpClient.newCall(request).execute();
			// 判断请求是否成功
			if (response.isSuccessful()) {
				result = response.body().string();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println("请求url:" + url);
		out.println("请求body:" + json);
		out.println("响应结果:" + result);
		return result;

	}
}

class TrustAnyTrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s)
			throws java.security.cert.CertificateException {

	}

	@Override
	public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s)
			throws java.security.cert.CertificateException {

	}

	@Override
	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		return new java.security.cert.X509Certificate[0];
	}
}

class TrustAnyHostnameVerifier implements HostnameVerifier {
	public boolean verify(String hostname, SSLSession session) {
		return true;
	}
}
