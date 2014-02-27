package com.example.ultils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;

public class Test extends AsyncTask<String, Void, String>{
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	static String respond = "";
	@Override
	protected String doInBackground(String... urls) {
		// TODO Auto-generated method stub
		
		try {
			String url = urls[0];
			System.out.println("Url: "+url);
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpPost = new HttpGet(url);
//			if (params != null) {
//				//httpPost.setEntity(new UrlEncodedFormEntity(params));
//			}

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			
			System.out.println("OK 4");
			respond = "";
			String line = null;
			while ((line = reader.readLine()) != null) {
				respond += line;
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return respond;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		//super.onPostExecute(result);
		System.out.println("in ra roi ne: "+ this.respond);
	}
	

}
