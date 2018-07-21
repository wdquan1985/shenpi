package com.arisen.shenpi.tool;

import io.jsonwebtoken.Header;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import java.io.IOException;

public class HttpClientTool {
	private static DefaultHttpClient httpclient = new DefaultHttpClient();
	private static Logger log = Logger.getLogger(HttpClient.class.getName());
	private static OAuthConsumer consumer = null;

	public static void setOAuthCredentials(String key, String secret) {
		log.info("Setting client oath key to " + key);
		log.info("Setting client oath secret to " + secret);
		consumer = new CommonsHttpOAuthConsumer(key, secret);
	}

	public static String[] getRequest(String url, String username, String password, String token, String sContentType, String sAcceptHeader, List<NameValuePair> nvpList) throws Exception {
		HttpGet get = new HttpGet(url);
		return finishRequest(get, username, password, token, null, sContentType, sAcceptHeader, nvpList);
	}

	public static String[] postRequest(String url, String username, String password, String token, String requestBody, String sContentType, String sAcceptHeader, List<NameValuePair> nvpList) throws Exception {
		HttpPost post = new HttpPost(url);
		return finishRequest(post, username, password, token, requestBody, sContentType, sAcceptHeader, nvpList);
	}

	public static String[] putRequest(String url, String username, String password, String token, String requestBody, String sContentType, String sAcceptHeader, List<NameValuePair> nvpList) throws Exception {
		HttpPut put = new HttpPut(url);
		return finishRequest(put, username, password, token, requestBody, sContentType, sAcceptHeader, nvpList);
	}

	public static String[] deleteRequest(String url, String username, String password, String token, String requestBody, String sContentType, String sAcceptHeader) throws Exception {
		HttpDeleteWithBody delete = new HttpDeleteWithBody(url);
		return finishRequest(delete, username, password, token, requestBody, sContentType, sAcceptHeader, null);
	}

	private static String[] finishRequest(HttpUriRequest method, String username, String password, String token, String requestBody, String sContentType, String sAcceptHeader, List<NameValuePair> nvpList) throws Exception {
		String sArgs = "";

		if (!StringUtils.isEmpty(requestBody)) {
			if ( requestBody.contains("&") && !requestBody.contains("&&") ) {
				String[] items = requestBody.split("&");
				for (String i: items)
					sArgs += " -d " + i;
			} else 	
				sArgs += " -d '" + requestBody + "'";
		}	

		if (nvpList != null) {
			for (NameValuePair key: nvpList) {
				key.getName();
				key.getValue();
				sArgs += " -d " + key.getName() + "='" + key.getValue() + "'";
				if (StringUtils.isEmpty(requestBody))
					requestBody = key.getName() + "=" + key.getValue() + "";
				else
					requestBody += "&" + key.getName() + "=" + key.getValue() + "";
			}
		}

		if (!StringUtils.isEmpty(sContentType)){
			sArgs += " -H \"Content-Type: " + sContentType + "\"";
			method.addHeader("Content-Type", sContentType);
		}
		if (!StringUtils.isEmpty(sAcceptHeader)) {
			sArgs += " -H \"Accept: " + sAcceptHeader + "\"";
			method.addHeader("Accept", sAcceptHeader);
		}  
		if (!StringUtils.isEmpty(token)) {
			sArgs += " -H \"Authorization: " + "bearer " + token + "\"";
			method.addHeader("Authorization", "bearer " + token);
		} 

		if (!StringUtils.isEmpty(requestBody)) {
			StringEntity entity = new StringEntity(requestBody, HTTP.UTF_8);
			BasicHeader basicHeader = new BasicHeader(HTTP.CONTENT_TYPE,sContentType);
			entity.setContentType(basicHeader);

			if (method instanceof HttpPut)
				((HttpPut)method).setEntity(entity);
			else if (method instanceof HttpPost)
				((HttpPost)method).setEntity(entity);
			else if (method instanceof HttpDeleteWithBody)
				((HttpDeleteWithBody)method).setEntity(entity);
		}

		if (method.getURI().getScheme().equalsIgnoreCase("https")) {
			sArgs += " --insecure";
			setupHTTPS(method);
		}

		if (consumer != null) {
			consumer.sign(method);
		} else if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
			sArgs += " -u " + username + ":" + password;
			
			// http://stackoverflow.com/questions/2014700/preemptive-basic-authentication-with-apache-httpclient-4
			// forcing authentication (needed for conductor api)
     		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
     		method.addHeader(new BasicScheme().authenticate(creds, method));
     		
     		setCredentials(method.getURI().getHost(), method.getURI().getPort(), username, password);
		}

		log.info("cmdline curl equivalent: curl -X " +method.getMethod().toString() + sArgs +" "+ method.getURI() );
		return processRequest(method, username, password);
	}	

	private static void setCredentials(String fqdn, int port, String username, String password) {
		if (!username.equals(""))
			httpclient.getCredentialsProvider().setCredentials(
					new AuthScope(fqdn, port),
					new UsernamePasswordCredentials(username, password));
	}

	private static void setupHTTPS(HttpUriRequest method) throws NoSuchAlgorithmException, KeyManagementException {
		// Override check cert validity to avoid SSL handshake exception
		// http://stackoverflow.com/questions/1828775/httpclient-and-ssl
		TrustManager easyTrustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain,	String authType) throws CertificateException {
				// do nothing
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain,	String authType) throws CertificateException {
				// do nothing
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}		    
		};

		X509HostnameVerifier verifier = new X509HostnameVerifier() {
			@Override
				public void verify(String string, SSLSocket ssls) throws IOException {
				}

			@Override
				public void verify(String string, X509Certificate xc) throws SSLException {
				}

			@Override
				public void verify(String string, String[] strings, String[] strings1) throws SSLException {
				}

			@Override
				public boolean verify(String string, SSLSession ssls) {
					return true;
				}
		};

		SSLContext sslcontext = SSLContext.getInstance("SSL");
		sslcontext.init(null, new TrustManager[] { easyTrustManager }, null);
		SSLSocketFactory socketFactory = new SSLSocketFactory(sslcontext); 
		socketFactory.setHostnameVerifier(verifier);
		Scheme sch = new Scheme("https", method.getURI().getPort()==-1?443:method.getURI().getPort(), socketFactory);
		httpclient.getConnectionManager().getSchemeRegistry().register(sch);
	}

	private static String[] processRequest(HttpUriRequest method, String username, String password)	throws Exception {
		String[] response = new String[3];
		String server = method.getURI().getHost();

		HttpResponse httpResponse = httpclient.execute(method);
		response[0] = Integer.toString(httpResponse.getStatusLine().getStatusCode());
		
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			//long len = entity.getContentLength();
			response[1] = EntityUtils.toString(entity);
		}	
		org.apache.http.Header[] headers = httpResponse.getHeaders("Location");
		if (null != headers && headers.length > 0) {
			String queueNumber = headers[0].getValue();
			response[2] = queueNumber;
		}else {
			response[2] = "";
		}
		return response;
	}

	// We override HttpEntityEnclosingRequestBase in order to allow a body to be passed in a delete request
	// Needed for RhevmApiClient class
	// http://stackoverflow.com/questions/3773338/httpdelete-with-body
	@NotThreadSafe
	private static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
		public static final String METHOD_NAME = "DELETE";
		public String getMethod() {
			return METHOD_NAME;
		}
		public HttpDeleteWithBody(final String uri) {
			super();
			setURI(URI.create(uri));
		}
		public HttpDeleteWithBody(final URI uri) {
			super();
			setURI(uri);
		}
		public HttpDeleteWithBody() {
			super();
		}
	}

//	private static String buildUrl(String protocol, String server, String port, String path) {
//		return (port == null) ? protocol + "://"+server+path : protocol + "://"+server+":"+port+path;	
//	}
} 