package com.trafficsign.activity;

import static com.trafficsign.ultils.Properties.serviceIp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.trafficsign.json.ResultDB;
import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.GlobalValue;
import com.trafficsign.ultils.NetUtil;
import com.trafficsign.ultils.Properties;
import com.trafficsign.ultils.UploadUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// if access to server ok
				System.out.print("phong");
				if (NetUtil.networkState(context) > Properties.INTERNET_SETTING) {
					final String upLoadServerUri = GlobalValue.getServiceAddress()
							+ Properties.TRAFFIC_SEARCH_AUTO;
					// get list result in DB is waiting for search auto
					ArrayList<ResultDB> listResult = new ArrayList<ResultDB>();
					listResult = DBUtil.getSavedSearch();
					// if have result is waiting for search auto
					if (listResult.size() > 0) {
						for (int i = 0; i < listResult.size(); i++) {
							ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
							parameters.add(new BasicNameValuePair("userID",
									listResult.get(i).getCreator()));
							parameters.add(new BasicNameValuePair("listLocate",
									listResult.get(i).getLocate()));
							//get json result
							String jsonString = "";
							jsonString = UploadUtils.uploadFile(listResult.get(i).getUploadedImage(),
									upLoadServerUri, parameters);
							Log.e("search", jsonString);
						}
					}
				}
			}

		}).start();

	}

}
