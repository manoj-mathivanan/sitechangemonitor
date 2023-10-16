package com.manoj.poc;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Mailer {

	
	public static void sendMail(String subject, String content) {
		sendMail(subject,content,"ma.manoj@gmail.com");
		//sendMail(subject,content,"rajkumar.thanudhasan@gmail.com");
		sendMail(subject,content,"ramshan2008@gmail.com");
	}
	
	public static void sendMail(String subject, String content, String to) {
		try {
			String url = "https://api.mailgun.net/v3/sandboxee266d9b20424c46a64f308837367a38.mailgun.org/messages";

			HttpClient client = getAllAcceptCertsClient();

			HttpPost post = new HttpPost(url);
			
			post.setHeader("Authorization", "Basic YXBpOmtleS01OTYwYTFiMjAyYWU0MDI1M2FjYmI4ZjcxY2JjYWQ2Mw==");

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("from", "ASIN_CHECKER<muchumanoj@sandboxee266d9b20424c46a64f308837367a38.mailgun.org>"));
			urlParameters.add(new BasicNameValuePair("to", to));
			urlParameters.add(new BasicNameValuePair("subject", subject));
			urlParameters.add(new BasicNameValuePair("text", content));

			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			HttpResponse response = client.execute(post);
			//System.out.println("\nSending 'POST' request to URL : " + url);
			//System.out.println("Post parameters : " + post.getEntity());
			//System.out.println("Response Code : " +  response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
	                        new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			System.out.println(subject + " - " + result.toString());
			

			

		}catch(Exception e) {
			System.out.println("Error sending mail");
			e.printStackTrace();
		}
	}
	
	public static String encode(String url)  
	{  
		try {  
			String encodeURL=URLEncoder.encode( url, "UTF-8" );  
			encodeURL = encodeURL.replaceAll(Pattern.quote("+"), "%20");
			return encodeURL;  
		} catch (UnsupportedEncodingException e) {  
			e.printStackTrace();
			return null;  
		}  
	}

	public static HttpClient getAllAcceptCertsClient() {
		try {
			SSLContext context = SSLContext.getInstance("TLSv1.2");
			TrustManager[] trustManager = new TrustManager[] {
					new X509TrustManager() {
						public X509Certificate[] getAcceptedIssuers() {
							return new X509Certificate[0];
						}
						public void checkClientTrusted(X509Certificate[] certificate, String str) {}
						public void checkServerTrusted(X509Certificate[] certificate, String str) {}
					}
			};
			context.init(null, trustManager, new SecureRandom());

			SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(context,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpClient client = HttpClientBuilder.create().setSSLSocketFactory(socketFactory).build();

			return client;
		}catch(Exception e) {return null;}

	}
	
	

}
