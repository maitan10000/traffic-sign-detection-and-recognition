package com.trafficsign.ultils;

import java.io.BufferedReader;
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
import org.apache.http.params.HttpParams;
import org.json.JSONArray;

import com.trafficsign.ultils.MyInterface.IAsyncHttpImageListener;
import com.trafficsign.ultils.MyInterface.IAsyncHttpListener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class HttpImageUtils extends AsyncTask<Void, Void, Void> {
	private IAsyncHttpImageListener httpImageListener = null;

	private Bitmap bitmap;
	private int extra = 0;

	private String url = "";

	public String getUrl() {
		return url;
	}

	public int getExtra() {
		return extra;
	}

	public void setExtra(int extra) {
		this.extra = extra;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public IAsyncHttpImageListener getHttpImageListener() {
		return httpImageListener;
	}

	public void setHttpImageListener(IAsyncHttpImageListener httpImageListener) {
		this.httpImageListener = httpImageListener;
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		this.bitmap = HttpUtil.downloadImage(url);
		return null;

	}

	@Override
	protected void onPostExecute(Void params) {
		// TODO Auto-generated method stub
		// super.onPostExecute(result);
		if (this.httpImageListener != null) {
			this.httpImageListener.onComplete(this.bitmap, this.extra);
		}
	}

}
