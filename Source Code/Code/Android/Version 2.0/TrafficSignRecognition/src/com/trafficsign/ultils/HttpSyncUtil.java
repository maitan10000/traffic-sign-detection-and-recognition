package com.trafficsign.ultils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;



import com.google.gson.reflect.TypeToken;
import com.trafficsign.json.CategoryJSON;
import com.trafficsign.json.ResultDB;
import com.trafficsign.json.ResultJSON;
import com.trafficsign.json.ResultShortJSON;
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
		/* Sync favorite */
		// URL for service
		String urlListFavorite = GlobalValue.getServiceAddress()
				+ Properties.MANAGE_FAVORITE_LIST + "?creator=" + this.user;
		// get all favorite from service and parse json to list Trafficinfoshort
		String favoriteResponse = HttpUtil.get(urlListFavorite);
		ArrayList<TrafficInfoShortJSON> listFavorite = new ArrayList<TrafficInfoShortJSON>();
		Type typeListFavorite = new TypeToken<ArrayList<TrafficInfoShortJSON>>() {
		}.getType();
		try {
			Date now = new Date(new java.util.Date().getTime());
			DateFormat fullDf = DateFormat.getDateTimeInstance(DateFormat.LONG, 
	                DateFormat.MEDIUM);
	     
		    String date =  fullDf.format(now);
		    

		    //favoriteResponse = favoriteResponse.replace("ICT", "GMT+07:00");
			listFavorite = Helper.fromJson(favoriteResponse, typeListFavorite);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// remove previous user's favorite list
		DBUtil.removeAllFavorite();
		if (listFavorite != null && listFavorite.size() > 0) {
			for (int i = 0; i < listFavorite.size(); i++) {
				DBUtil.addFavorite(listFavorite.get(i), this.user);
			}
		}
		/* End sync favorite */
		/* Sync history */
		// get listHistory
		String urlListHistory = GlobalValue.getServiceAddress()
				+ Properties.TRAFFIC_HISTORY_LIST + "?creator=" + this.user;
		String listHistoryResponse = HttpUtil.get(urlListHistory);
		ArrayList<ResultShortJSON> listHistory = new ArrayList<ResultShortJSON>();
		Type typeListHistory = new TypeToken<ArrayList<ResultShortJSON>>() {
		}.getType();
		listHistory = Helper.fromJson(listHistoryResponse, typeListHistory);
		// if listHistory is not empty
		if (listHistory != null && listHistory.size() > 0) {
			DBUtil.deleteAllResult(); // delete previous user's result
			for (int i = 0; i < listHistory.size(); i++) {
				String urlViewHistory = GlobalValue.getServiceAddress()
						+ Properties.TRAFFIC_HISTORY_VIEW + "?id="
						+ listHistory.get(i).getResultID();
				ResultJSON resultJSON = new ResultJSON();
				String historyDetail = HttpUtil.get(urlViewHistory);
				Type typeResultJson = new TypeToken<ResultJSON>() {
				}.getType();
				resultJSON = Helper.fromJson(historyDetail, typeResultJson);
				// download uploaded image if image is not exist
				String imagePath = GlobalValue.getAppFolder()
						+ Properties.SAVE_IMAGE_FOLDER
						+ splitImageName(resultJSON.getUploadedImage());
				File image = new File(imagePath);
				if (!image.exists()) {
					String imageUrl = GlobalValue.getServiceAddress()
							+ resultJSON.getUploadedImage();
					HttpUtil.downloadImage(imageUrl, imagePath);
				}
				// save result to db
				ResultDB resultDB = new ResultDB();
				resultDB.setCreateDate(resultJSON.getCreateDate());
				resultDB.setCreator(resultJSON.getCreator());
				resultDB.setLocate(Helper.toJson(resultJSON.getListTraffic()));
				resultDB.setResultID(resultJSON.getResultID());
				resultDB.setUploadedImage(imagePath);
				DBUtil.addResult(resultDB, user);
			}
		}
		/* End sync history */
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

	public static String splitImageName(String input) {
		String[] imagePath = input.split("/");

		return imagePath[imagePath.length - 1];
	}

}
