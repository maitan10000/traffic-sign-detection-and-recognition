package com.trafficsign.ultils;

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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;

import com.trafficsign.activity.MainActivity;
import com.trafficsign.ultils.MyInterface.IAsyncHttpListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class HttpAsyncUtil extends AsyncTask<Void, Void, Void> {
	private IAsyncHttpListener httpListener = null;
	private Context context;
	private ProgressDialog dialog;
	private String respond = "";
	private String method = "GET";
	private List<NameValuePair> parameters;
	private String url = "";

	public HttpAsyncUtil(Context ctx) {
		this.context = ctx;
		dialog = new ProgressDialog(this.context);
	}

	public HttpAsyncUtil() {

	}

	public IAsyncHttpListener getHttpListener() {
		return httpListener;
	}

	public void setHttpListener(IAsyncHttpListener httpListener) {
		this.httpListener = httpListener;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<NameValuePair> getParameters() {
		return parameters;
	}

	public void setParameters(List<NameValuePair> parameters) {
		this.parameters = parameters;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		respond = "";
		// if accessed to server
		if(NetUtil.isAccessService() == true){
			if (this.method.toUpperCase().equals("GET")) {
				respond = HttpUtil.get(url);
			} else {
				respond = HttpUtil.post(url, parameters);
			}
		} else{ // if can not access to server
			respond = "serverFail";
		}
		Log.e("http",respond);
	
		return null;
	}

	@Override
	protected void onPreExecute() {
		if (dialog != null) {
			dialog.setMessage("Vui lòng đợi trong giây lát");
			dialog.setCancelable(false);
			dialog.show();
		}

	}

	@Override
	protected void onPostExecute(Void params) {
		// TODO Auto-generated method stub
		// super.onPostExecute(result);
		if (this.httpListener != null) {
			this.httpListener.onComplete(this.respond);
			if (dialog != null) {
				dialog.dismiss();
			}
		}
	}
}
