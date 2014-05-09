package com.trafficsign.ultils;

import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetUtil {
	/**
	 * Check connection state Return 2 if wifi Return 1 if not type wifi Return
	 * 0 if can not access to server
	 */

	public static int networkState(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo actiNetworkInfo = cm.getActiveNetworkInfo();
		boolean isNotConnectServer = actiNetworkInfo == null
				|| !actiNetworkInfo.isConnected() || !isAccessService();
		if (isNotConnectServer == true) {
			// not access to server
			return 0;
		} else if (actiNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			// access to server using wifi
			return 2;
		} else {
			// access to server not using wifi
			return 1;
		}
	}

	public static Boolean isAccessService() {
		try {
			// Test connect with timeout 2s
			URL myUrl = new URL(GlobalValue.getServiceAddress());
			URLConnection connection = myUrl.openConnection();
			connection.setConnectTimeout(5000);
			connection.connect();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
