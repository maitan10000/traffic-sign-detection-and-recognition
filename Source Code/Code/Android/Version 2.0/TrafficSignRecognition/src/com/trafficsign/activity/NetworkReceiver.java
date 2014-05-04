package com.trafficsign.activity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.trafficsign.json.FavoriteJSON;
import com.trafficsign.json.ResultDB;
import com.trafficsign.json.ResultInput;
import com.trafficsign.json.ResultJSON;
import com.trafficsign.json.ResultShortJSON;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.json.TrafficInfoShortJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.GlobalValue;
import com.trafficsign.ultils.Helper;
import com.trafficsign.ultils.HttpUtil;
import com.trafficsign.ultils.NetUtil;
import com.trafficsign.ultils.Properties;
import com.trafficsign.ultils.SyncUtil;
import com.trafficsign.ultils.UploadUtils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver {	

	@Override
	public void onReceive(final Context context, final Intent intent) {
		SyncUtil syncUtil = new SyncUtil();
		syncUtil.syncUserData(context);
	}

	// update traffic sign from server
	/*
	public static void updateTraffic() {
		// URL for service
		String urlGetListTraffic = GlobalValue.getServiceAddress()
				+ Properties.TRAFFIC_SEARCH_MANUAL + "?name=";
		String urlGetTrafficDetail = GlobalValue.getServiceAddress()
				+ Properties.TRAFFIC_TRAFFIC_VIEW + "?id=";		

		String listTrafficJSON = HttpUtil.get(urlGetListTraffic);
		ArrayList<TrafficInfoShortJSON> listInfoShortJSONs = new ArrayList<TrafficInfoShortJSON>();
		Type typeListTrafficShort = new TypeToken<ArrayList<TrafficInfoShortJSON>>() {
		}.getType();
		listInfoShortJSONs = Helper.fromJson(listTrafficJSON,
				typeListTrafficShort);
		// get each traffic details and add to DB
		if (listInfoShortJSONs.size() > 0) {
			String urlGetTrafficDetailFull = "";
			int dbReturn; // variable to know db return
			for (int i = 0; i < listInfoShortJSONs.size(); i++) {
				urlGetTrafficDetailFull = urlGetTrafficDetail
						+ listInfoShortJSONs.get(i).getTrafficID();
				// get traffic detail from service and parse json
				// TrafficInfoJson
				String trafficJSON = HttpUtil.get(urlGetTrafficDetailFull);
				TrafficInfoJSON trafficInfoJSON = new TrafficInfoJSON();
				trafficInfoJSON = Helper.fromJson(trafficJSON,
						TrafficInfoJSON.class);
				// add traffic to DB
				dbReturn = DBUtil.updateTraffic(trafficInfoJSON);
				// download image if not exist
				String savePath = GlobalValue.getAppFolder()
						+ Properties.MAIN_IMAGE_FOLDER
						+ trafficInfoJSON.getTrafficID() + ".jpg";
				File image = new File(savePath);
				if (!image.exists()) {
					String imageLink = GlobalValue.getServiceAddress()
							+ trafficInfoJSON.getImage();
					HttpUtil.downloadImage(imageLink, savePath);
				}

				Log.e("DB", dbReturn + "");
			}
		}
	}
*/
}
