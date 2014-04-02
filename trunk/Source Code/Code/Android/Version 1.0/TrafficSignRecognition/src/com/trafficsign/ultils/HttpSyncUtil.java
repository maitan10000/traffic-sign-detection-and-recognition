package com.trafficsign.ultils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.trafficsign.json.CategoryJSON;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.json.TrafficInfoShortJSON;
import com.trafficsign.ultils.MyInterface.IAsyncHttpListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class HttpSyncUtil extends AsyncTask<Void, Void, Void> {
	private Context context;
	private ProgressDialog dialog;
	private String user;
	private String response;
	private IAsyncHttpListener httpListener = null;

	
	public void setHttpListener(IAsyncHttpListener httpListener) {
		this.httpListener = httpListener;
	}


	public HttpSyncUtil(Context context) {
		this.context = context;
		dialog = new ProgressDialog(this.context);
	}
	

	public void setUser(String user) {
		this.user = user;
	}


	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.FULL,
				DateFormat.FULL).create();
		// URL for service
		String urlListFavorite = Properties.serviceIp
				+ Properties.MANAGE_FAVORITE_LIST + "?creator=" + this.user;
		// get all favorite from service and parse json to list Trafficinfoshort
		String favoriteResponse = HttpUtil.get(urlListFavorite);
		ArrayList<TrafficInfoShortJSON> listFavorite = new ArrayList<TrafficInfoShortJSON>();
		Type typeListFavorite = new TypeToken<ArrayList<TrafficInfoShortJSON>>() {
		}.getType();
		listFavorite = gson.fromJson(favoriteResponse, typeListFavorite);
		// remove previous user's favorite list
		DBUtil.removeFavorite();
		if (listFavorite.size() > 0) {
			for (int i = 0; i < listFavorite.size(); i++) {
				DBUtil.addFavorite(listFavorite.get(i), this.user);
			}
		}
		response = favoriteResponse;
		return null;
	}

	@Override
	protected void onPreExecute() {
		if (dialog != null) {
			dialog.setMessage("Đồng bộ hóa..");
			dialog.setCancelable(false);
			dialog.show();
		}

	}

	@Override
	protected void onPostExecute(Void params) {
		// TODO Auto-generated method stub
		// super.onPostExecute(result);

		if (this.httpListener != null) {
			this.httpListener.onComplete(this.response);
			if (dialog != null) {
				dialog.dismiss();
			}
		}

	}

}
