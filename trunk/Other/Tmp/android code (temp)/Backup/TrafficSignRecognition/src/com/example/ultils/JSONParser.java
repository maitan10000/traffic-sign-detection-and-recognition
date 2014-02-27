package com.example.ultils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class JSONParser extends AsyncTask<String, Void, String> {

	private static InputStream is = null;
	private static JSONObject jObj = null;
	private static String json = "";
	private static String respond = "";

	// constructor
	public JSONParser() {

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
			jObj = new JSONObject(respond);
//			System.out.println("test ra ne :" + respond);
//			//jObj = new JSONObject(respond);
//			System.out.println("ok 0");
//			JSONArray jArray = new JSONArray(respond);
//			System.out.println(jArray.getJSONObject(0).getString("name"));

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
	}

	public static JSONObject getjObj() {
		return jObj;
	}

	public static void setjObj(JSONObject jObj) {
		JSONParser.jObj = jObj;
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
