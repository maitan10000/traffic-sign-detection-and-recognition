package com.trafficsign.ultils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
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
import com.google.gson.reflect.TypeToken;
import com.trafficsign.json.CategoryJSON;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.json.TrafficInfoShortJSON;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class HttpDatabaseUtil extends AsyncTask<Void, Void, Void> {
	private Context context;
	private ProgressDialog dialog;

	public HttpDatabaseUtil(Context context) {
		this.context = context;
		dialog = new ProgressDialog(this.context);
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		// URL for service
		String urlCategory = Properties.serviceIp
				+ Properties.TRAFFIC_LIST_CATEGORY;
		String urlGetListTraffic = Properties.serviceIp
				+ Properties.TRAFFIC_SEARCH_MANUAL + "?name=";
		String urlGetTrafficDetail = Properties.serviceIp
				+ Properties.TRAFFIC_TRAFFIC_VIEW + "?id=";

		// get all category from service and parse json to list CategoryJSON
		String catResponse = HttpUtil.get(urlCategory);
		ArrayList<CategoryJSON> listCategory = new ArrayList<CategoryJSON>();
		Type typeListCategory = new TypeToken<ArrayList<CategoryJSON>>() {
		}.getType();
		listCategory = gson.fromJson(catResponse, typeListCategory);
		// add category to DB
		if (listCategory.size() > 0) {
			Long dbReturn;
			for (int i = 0; i < listCategory.size(); i++) {
				dbReturn = DBUtil.insertCategory(listCategory.get(i));
				Log.e("DB", dbReturn.toString());
			}
		}
		// End add category

		/*
		 * get all traffic (short) from service and parse json to list
		 * TrafficInfoJsonShort
		 */

		String listTrafficJSON = HttpUtil.get(urlGetListTraffic);
		ArrayList<TrafficInfoShortJSON> listInfoShortJSONs = new ArrayList<TrafficInfoShortJSON>();
		Type typeListTrafficShort = new TypeToken<ArrayList<TrafficInfoShortJSON>>() {
		}.getType();
		listInfoShortJSONs = gson.fromJson(listTrafficJSON,
				typeListTrafficShort);
		// get each traffic details and add to DB
		if (listInfoShortJSONs.size() > 0) {
			String urlGetTrafficDetailFull = "";
			Long dbReturn; // variable to know db return
			for (int i = 0; i < listInfoShortJSONs.size(); i++) {
				urlGetTrafficDetailFull = urlGetTrafficDetail
						+ listInfoShortJSONs.get(i).getTrafficID();
				// get traffic detail from service and parse json
				// TrafficInfoJson
				String trafficJSON = HttpUtil.get(urlGetTrafficDetailFull);
				TrafficInfoJSON trafficInfoJSON = new TrafficInfoJSON();
				trafficInfoJSON = gson.fromJson(trafficJSON,
						TrafficInfoJSON.class);
				// add traffic to DB
				dbReturn = DBUtil.insertTraffic(trafficInfoJSON);
				String savePath = GlobalValue.getAppFolder()
						+ Properties.MAIN_IMAGE_FOLDER
						+ trafficInfoJSON.getTrafficID() + ".jpg";
				String imageLink = Properties.serviceIp + trafficInfoJSON.getImage();
				HttpUtil.downloadImage(imageLink, savePath);				
				Log.e("DB", dbReturn.toString());
			}
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		if (dialog != null) {
			dialog.setMessage("Loading");
			dialog.setCancelable(false);
			dialog.show();
		}

	}

	@Override
	protected void onPostExecute(Void params) {
		// TODO Auto-generated method stub
		// super.onPostExecute(result);

		if (dialog != null) {
			dialog.dismiss();
		}

	}

}
