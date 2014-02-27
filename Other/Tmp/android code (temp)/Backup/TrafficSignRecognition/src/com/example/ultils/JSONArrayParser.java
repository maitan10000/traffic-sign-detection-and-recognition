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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;



import com.example.ultils.MyInterface.IAsyncFetchListener;


import android.os.AsyncTask;


public class JSONArrayParser extends AsyncTask<String, Void, String> {
	
	private IAsyncFetchListener fetchListener = null;
	private static InputStream is = null;
	private  JSONArray jObjArr = null;
	private static String respond = "";

	// constructor
	public JSONArrayParser() {

	}

	@Override
	protected String doInBackground(String... urls) {
		// TODO Auto-generated method stub

		try {
			String url = urls[0];
			System.out.println("Url: " + url);
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpPost = new HttpGet(url);
			// if (params != null) {
			// //httpPost.setEntity(new UrlEncodedFormEntity(params));
			// }

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			respond = "";
			String line = null;
			while ((line = reader.readLine()) != null) {
				respond += line;
			}
			is.close();
			System.out.println("response: " + respond);
			this.jObjArr = new JSONArray(respond);
			System.out.println(jObjArr.length());

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
		// super.onPostExecute(result);
		if(this.fetchListener != null)
		{
			JSONArray jsonArr = this.getjObjArr();
			this.fetchListener.onComplete(jsonArr);
		}
		
	}

	public  JSONArray getjObjArr() {
		return this.jObjArr;
	}
	
	//set listener
	public void setListener(IAsyncFetchListener listener)
	{
		this.fetchListener = listener;		
	}



	// ///////////////////
	/*public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {

		// Making HTTP request
		try {
			// defaultHttpClient
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpPost = new HttpGet(url);
			if (params != null) {
				// httpPost.setEntity(new UrlEncodedFormEntity(params));
			}

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			Log.e("JSON", json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	} */
	
}
