package com.ht.huaxin.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpUtils {

	private static final Integer timeoutConnection = 3000;
	private static final Integer timeoutSocket = 5000;
	
	public static JSONObject get(String url) throws ClientProtocolException, IOException, JSONException{
		HttpGet httpGet = new HttpGet(url);
//		httpGet.addHeader("Content-Type", "application/json");
		
		HttpResponse response= getHttpClient().execute(httpGet);
		String responseBody = getASCIIContentFromEntity(response.getEntity());
		Log.i("debug", responseBody);
		JSONObject json = new JSONObject(responseBody);
		return json;
	}
	
	public static HttpClient getHttpClient(){
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		HttpClient httpClient = new DefaultHttpClient(httpParameters);
		
		return httpClient;
	}
	
	public static String getASCIIContentFromEntity(HttpEntity entity)
			throws IllegalStateException, IOException {
		InputStream in = entity.getContent();
		StringBuffer out = new StringBuffer();
		int n = 1;
		while (n > 0) {
			byte[] b = new byte[4096];
			n = in.read(b);
			if (n > 0)
				out.append(new String(b, 0, n));
		}
		return out.toString();
	}
}
