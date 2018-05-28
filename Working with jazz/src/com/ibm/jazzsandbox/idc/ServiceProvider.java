package com.ibm.jazzsandbox.idc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

public class ServiceProvider {
	
	URL initialiser;
	
	
//	https://135.188.242.28:8443/IgniteClassify/idc.html#/?&token=0bf3f6fb-1456-3596-a2f8-20440ed610d4&tokenExpiryDateTime=1526554675000&user=IgniteAdmin
	String url;
	
	Map<String,String> parameters;
	
	private static int m =0;
	
	
	public ServiceProvider(String url, Map<String, String> parameters) {
	
		this.url = url;
		
		this.parameters = parameters;
	}


	public void doPostRequest() throws Throwable
	{
		
		
		doTrustToCertificates();
		
		initialiser = new URL(url);
		
		
		
		HttpURLConnection connection =(HttpURLConnection) initialiser.openConnection();
		
		connection.setDoInput(true);
		
		connection.setDoOutput(true);
		
	//	 connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		
		StringBuffer requestURL = new StringBuffer();
		
	//--------------------	connection.getRequestProperties(); ---------------
		


		
		
		
		
		if(parameters !=null && parameters.size() > 0) {
		
		
			
		parameters.forEach((k,l) -> {
			
			try {
				
				
					requestURL.append(URLEncoder.encode(k,"UTF-8"))
					.append("=")
					.append(URLEncoder.encode(l, "UTF-8"))
					.append("&");
				
				
			} catch (UnsupportedEncodingException e) {
					
				System.out.println("Exception is :"+e.getClass().getSimpleName());
				
				e.printStackTrace();
			}
	});
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		
		writer.write(requestURL.toString());
		
	writer.flush();
	}
		
	InputStream s = connection.getInputStream();
	BufferedReader bf = new BufferedReader(new InputStreamReader(s));
		String m="";
		
		String line;
		while(( line = bf.readLine()) != null)
		{
		
			m+=line;
			
		}
		
		System.out.println(m);
		
	//	String s = req.getParameter("client_id");
		
		System.out.println(initialiser.getContent().toString());
		
	}

	 // trusting all certificate 
	 public void doTrustToCertificates() throws Exception {
	      //  Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	        TrustManager[] trustAllCerts = new TrustManager[]{
	                new X509TrustManager() {
	                    public X509Certificate[] getAcceptedIssuers() {
	                        return null;
	                    }

	                    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
	                        return;
	                    }

	                    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
	                        return;
	                    }
	                }
	        };

	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        HostnameVerifier hv = new HostnameVerifier() {
	        	
	        	@Override
	            public boolean verify(String urlHostName, SSLSession session) {
	                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
	                    System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
	                }
	                return true;
	            }

				
				
	        };
	        HttpsURLConnection.setDefaultHostnameVerifier(hv);
	    }
	
	}
