package com.trafficsign.activity;

import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.HttpDatabaseUtil;
import com.trafficsign.ultils.NetUtil;
import com.trafficsign.ultils.Properties;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	int networkFlag;
	@Override
	public void onReceive(final Context context, Intent intent) {
		final SharedPreferences sharedPreferences = context
				.getSharedPreferences(Properties.SHARE_PREFERENCE_SETTING, 0);
		boolean wifiStatus = sharedPreferences.getBoolean(
				Properties.SHARE_PREFERENCE_KEY_WIFI, true);
		networkFlag = 1;
		if (wifiStatus == false) {
			networkFlag = 0;
		}
		// TODO Auto-generated method stub
		Log.e("alarm", "alarm trigger");

		// chi chay khi dong bo lan dau da hoan thanh hoac update truoc do da
		// hoan thanh de tranh conflict
		if ("sync".equals(sharedPreferences.getString(
				Properties.SHARE_PREFERENCE__KEY_TRAFFIC_SYNC, ""))
				&& sharedPreferences
						.getBoolean(
								Properties.SHARE_PREFERENCE__KEY_TRAFFIC_UPDATE_RUNNING,
								false) == false) {
			final Editor editor = sharedPreferences.edit();
			editor.putBoolean(
					Properties.SHARE_PREFERENCE__KEY_TRAFFIC_UPDATE_RUNNING,
					true);
			editor.commit();
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						if (NetUtil.networkState(context) > networkFlag) {
							DBUtil.updateTrafficsign();
						}
						
					} finally {
						editor.putBoolean(
								Properties.SHARE_PREFERENCE__KEY_TRAFFIC_UPDATE_RUNNING,
								false);
						editor.commit();
						Log.e("alarm", "update complete");
					}

				}
			}).start();
		}

	}

}
