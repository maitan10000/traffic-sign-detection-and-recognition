package com.trafficsign.ultils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpUtil {
	// get reponse from service for method GET
	public static String get(String url) {
		return http(url, "GET", null);
	}

	// get reponse from service for method POST
	public static String post(String url, List<NameValuePair> parameters) {
		return http(url, "POST", parameters);
	}

	// get reponse from service for method POST and GET
	private static String http(String url, String method,
			List<NameValuePair> parameters) {
		String respond = "";
		try {
			// create client
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse;
			if (method.toUpperCase().equals("GET")) {
				HttpGet request = new HttpGet(url);
				httpResponse = httpClient.execute(request);
			} else {
				HttpPost request = new HttpPost(url);
				request.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
				httpResponse = httpClient.execute(request);
			}

			// Begin get respond
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream is = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			String line = null;
			while ((line = reader.readLine()) != null) {
				respond += line;
			}
			is.close();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respond;
	}

	public static boolean downloadImage(String url, String savePath) {
		Bitmap bitmap = downloadImage(url);
		FileOutputStream out = null;
		try {
			if (bitmap != null) {
				out = new FileOutputStream(savePath);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Throwable ignore) {
				}
			}
		}
		return false;
	}

	// download image
	public static Bitmap downloadImage(String url) {
		Bitmap bitmap = null;
		InputStream stream = null;
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inSampleSize = 1;

		try {
			stream = getHttpConnection(url);
			if (stream != null) {
				bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
				stream.close();
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return bitmap;
	}

	// Makes HttpURLConnection and returns InputStream
	private static InputStream getHttpConnection(String urlString)
			throws IOException {
		InputStream stream = null;
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();

		try {
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			httpConnection.setRequestMethod("GET");
			httpConnection.connect();

			// if (httpConnection.getResponseCode() ==
			// HttpURLConnection.HTTP_OK) {

			// }
			stream = httpConnection.getInputStream();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return stream;
	}
}