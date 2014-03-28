package com.trafficsign.ultils;

import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {
	/**
	 * Check connection state Return 2 if wifi Return 1 if not type wifi Return
	 * 0 if no internet connection
	 */
	public static int networkState(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo actiNetworkInfo = cm.getActiveNetworkInfo();
		// if dose not have internet connection
		if (actiNetworkInfo == null || !actiNetworkInfo.isConnected()
				|| !isAccessService()) {
			return 0;
		} else if (actiNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) { // if
																					// wifi
			return 2;
		}
		return 1;
	}

	public static Boolean isAccessService() {
		try{
			//Test connect with timeout 2s
	        URL myUrl = new URL(GlobalValue.getServiceAddress());
	        URLConnection connection = myUrl.openConnection();
	        connection.setConnectTimeout(2000);
	        connection.connect();
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return false;
	}
}
