package com.trafficsign.activity;

import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.GlobalValue;
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
		boolean isWifiOnly = sharedPreferences.getBoolean(
				Properties.SHARE_PREFERENCE_KEY_WIFI, true);
		networkFlag = 1;
		if (isWifiOnly == false) {
			networkFlag = 0;
		}
		// TODO Auto-generated method stub
		Log.e("alarm", "alarm trigger");

		// chi chay khi dong bo lan dau da hoan thanh hoac update truoc do da
		// hoan thanh de tranh conflict
		if (sharedPreferences.getString(
				Properties.SHARE_PREFERENCE__KEY_TRAFFIC_SYNC, "").length() > 0  && GlobalValue.isUpdating == false) {
			GlobalValue.isUpdating = true;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						if (NetUtil.networkState(context) > networkFlag) {
							DBUtil.updateTrafficsign(context);
						}
						
					} finally {
						GlobalValue.isUpdating = false;
						Log.e("alarm", "update complete");
					}

				}
			}).start();
		}

	}
}
