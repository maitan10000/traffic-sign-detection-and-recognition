package com.trafficsign.ultils;

import java.io.BufferedReader;
import java.io.File;
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


import com.google.gson.reflect.TypeToken;
import com.trafficsign.json.CategoryJSON;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.json.TrafficInfoShortJSON;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.widget.Toast;

public class HttpDatabaseUtil extends AsyncTask<Void, Void, Void> {
	private Context context;
	private ProgressDialog dialog;
	private int networkFlag;
	private boolean isSyncComplete = true;

	public HttpDatabaseUtil(Context context) {
		this.context = context;
		dialog = new ProgressDialog(this.context);
	}

	@Override
	protected Void doInBackground(Void... params) {
		if (NetUtil.networkState(context) > networkFlag) {
			try {
				// TODO Auto-generated method stub
				// URL for service
				String urlCategory = Properties.serviceIpOnline
						+ Properties.TRAFFIC_LIST_CATEGORY;
				String urlGetListTraffic = Properties.serviceIpOnline
						+ Properties.TRAFFIC_SEARCH_MANUAL + "?name=";
				String urlGetTrafficDetail = Properties.serviceIpOnline
						+ Properties.TRAFFIC_TRAFFIC_VIEW + "?id=";

				// get all category from service and parse json to list
				// CategoryJSON
				String catResponse = HttpUtil.get(urlCategory);
				ArrayList<CategoryJSON> listCategory = new ArrayList<CategoryJSON>();
				Type typeListCategory = new TypeToken<ArrayList<CategoryJSON>>() {
				}.getType();
				listCategory = Helper.fromJson(catResponse, typeListCategory);
				// add category to DB
				if (listCategory != null && listCategory.size() > 0) {
					Long dbReturn;
					for (int i = 0; i < listCategory.size(); i++) {
						if (DBUtil.checkCategory(listCategory.get(i)
								.getCategoryID()) == false) {
							dbReturn = DBUtil.insertCategory(listCategory
									.get(i));
							Log.e("DB", dbReturn.toString());
						}

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
				listInfoShortJSONs = Helper.fromJson(listTrafficJSON,
						typeListTrafficShort);
				// get each traffic details and add to DB
				if (listInfoShortJSONs != null && listInfoShortJSONs.size() > 0) {
					Log.e("number traffic", listInfoShortJSONs.size() + "");
					String urlGetTrafficDetailFull = "";
					Long dbReturn; // variable to know db return
					for (int i = 0; i < listInfoShortJSONs.size(); i++) {
						if ("106".equals(listInfoShortJSONs.get(i)
								.getTrafficID())
								|| "110b".equals(listInfoShortJSONs.get(i)
										.getTrafficID())) {
							Log.e("traffic", "chua lai bien bao"
									+ listInfoShortJSONs.get(i).getTrafficID());
						} else {
							urlGetTrafficDetailFull = urlGetTrafficDetail
									+ listInfoShortJSONs.get(i).getTrafficID();
							// get traffic detail from service and parse json
							// TrafficInfoJson
							String trafficJSON = HttpUtil
									.get(urlGetTrafficDetailFull);
							TrafficInfoJSON trafficInfoJSON = new TrafficInfoJSON();
							trafficInfoJSON = Helper.fromJson(trafficJSON,
									TrafficInfoJSON.class);
							// add traffic to DB
							if (DBUtil.checkTraffic(trafficInfoJSON
									.getTrafficID()) == false) {
								dbReturn = DBUtil
										.insertTraffic(trafficInfoJSON);
								Log.e("DB", dbReturn + "");
							}
							String savePath = GlobalValue.getAppFolder()
									+ Properties.MAIN_IMAGE_FOLDER
									+ trafficInfoJSON.getTrafficID() + ".jpg";
							File image = new File(savePath);
							if (!image.exists()) {
								String imageLink = Properties.serviceIpOnline
										+ trafficInfoJSON.getImage();
								if (HttpUtil.downloadImage(imageLink, savePath)) {
									Log.e("DB Image", savePath);
								}

							}

							Log.e("Number", String.valueOf(i + 1));

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				if (dialog != null) {
					dialog.dismiss();
				}
				Toast.makeText(
						context,
						"Quá trinh đồng bộ chưa được hoàn tất, vui lòng kiểm tra kết nối và khởi động lại ứng dụng",
						Toast.LENGTH_LONG).show();
			}

		} else {
			isSyncComplete = false;
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		final SharedPreferences pref = context.getSharedPreferences(
				Properties.SHARE_PREFERENCE_SETTING, Context.MODE_PRIVATE);
		boolean wifiStatus = pref.getBoolean(
				Properties.SHARE_PREFERENCE_KEY_WIFI, true);
		networkFlag = 1;
		if (wifiStatus == false) {
			networkFlag = 0;
		}
		if (dialog != null) {
			dialog.setMessage("Đang tải dữ liệu");
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
		if(isSyncComplete == true){
			// set flag into share preference
			SharedPreferences sharedPreferences = context.getSharedPreferences(
					Properties.SHARE_PREFERENCE_SETTING, 0);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString(Properties.SHARE_PREFERENCE__KEY_TRAFFIC_SYNC, "sync");
			editor.commit();
		}
		
	}

}
