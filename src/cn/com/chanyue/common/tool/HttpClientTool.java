package cn.com.chanyue.common.tool;

import java.util.List;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


/**
 * HttpClient提交工具类
 * 
 * @author Howe
 * 
 */
public class HttpClientTool {

	/**
	 * POST提交
	 * 
	 * @param url
	 * @return
	 */
	public static String post(String url, List<NameValuePair> params) {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					"UTF-8");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			HttpEntity httpEntity = response.getEntity();
			return EntityUtils.toString(httpEntity, "UTF-8");
		} catch (Exception e) {
			return null;
		} finally {
			post.releaseConnection();
			client.getConnectionManager().shutdown();
		}
	}

	/**
	 * Get提交
	 * 
	 * @param url
	 * @return
	 */
	public static String get(String url) {

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		try {

			HttpResponse response = client.execute(get);
			HttpEntity httpEntity = response.getEntity();
			return EntityUtils.toString(httpEntity, "UTF-8");
		} catch (Exception e) {
			return null;
		} finally {
			get.releaseConnection();
			client.getConnectionManager().shutdown();
		}
	}
	
	/**
	 * Https Post提交
	 * @param url
	 * @param params
	 * @return
	 */
	public static String httpsPost(String url, List<NameValuePair> params) {
		String responseContent = null;
		HttpClient client = new DefaultHttpClient();
		//创建TrustManager
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		//这个好像是HOST验证
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
			public void verify(String arg0, SSLSocket arg1) throws IOException {}
			public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {}
			public void verify(String arg0, X509Certificate arg1) throws SSLException {}
		};
		try {

			SSLContext ctx = SSLContext.getInstance("SSL");
			//使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);
			//创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			socketFactory.setHostnameVerifier(hostnameVerifier);
			//通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			client.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443));
			HttpPost post = new HttpPost(url);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					"UTF-8");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			HttpEntity httpEntity = response.getEntity(); // 获取响应实体
			if (httpEntity != null) {
				responseContent = EntityUtils.toString(httpEntity, "UTF-8");
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			client.getConnectionManager().shutdown();
		}
		return responseContent;
	}
	
	/**
	 * HTTPS Get 提交
	 * @param url
	 * @return
	 */
	public static String httpsGet(String url) {
		String responseContent = null;
		HttpClient client = new DefaultHttpClient();
		//创建TrustManager
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		//这个好像是HOST验证
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
			public void verify(String arg0, SSLSocket arg1) throws IOException {}
			public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {}
			public void verify(String arg0, X509Certificate arg1) throws SSLException {}
		};
		try {

			SSLContext ctx = SSLContext.getInstance("SSL");
			//使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);
			//创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			socketFactory.setHostnameVerifier(hostnameVerifier);
			//通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			client.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443));
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			HttpEntity httpEntity = response.getEntity(); // 获取响应实体
			if (httpEntity != null) {
				responseContent = EntityUtils.toString(httpEntity, "UTF-8");
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			client.getConnectionManager().shutdown();
		}
		return responseContent;
	}
	
}
