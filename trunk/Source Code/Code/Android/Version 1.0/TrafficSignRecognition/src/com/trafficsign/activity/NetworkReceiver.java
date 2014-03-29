package com.trafficsign.activity;

import static com.trafficsign.ultils.Properties.serviceIp;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.trafficsign.json.ResultDB;
import com.trafficsign.json.ResultJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.GlobalValue;
import com.trafficsign.ultils.NetUtil;
import com.trafficsign.ultils.Properties;
import com.trafficsign.ultils.UploadUtils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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
							// get result from jsonstring to prepare for put to listresult activity
							ResultJSON resultJson = new ResultJSON();
							Gson gson = new Gson();
							resultJson = gson.fromJson(jsonString,
									ResultJSON.class);
							byte[] dataBytes = null;
							try {
								dataBytes = ConvertUtil.object2Bytes(resultJson);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String date = listResult.get(i).getCreateDate().toString();
							/*NOTIFICATION*/
							NotificationCompat.Builder mBuilder =  new NotificationCompat.Builder(context)
						    .setSmallIcon(R.drawable.ic_launcher)
						    .setContentTitle("Kết quả nhận diện")
						    .setContentText(listResult.get(i).getCreateDate().toString());
							Intent resultIntent = new Intent(context, ListResultActivity.class);
							resultIntent.putExtra("resultJson", dataBytes);
							resultIntent.putExtra("imagePath", listResult.get(i).getUploadedImage());
							// The stack builder object will contain an artificial back stack for the
							// started Activity.
							// This ensures that navigating backward from the Activity leads out of
							// your application to the Home screen.
							TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
							// Adds the back stack for the Intent (but not the Intent itself)
							stackBuilder.addParentStack(MainActivity.class);
							// Adds the Intent that starts the Activity to the top of the stack
							stackBuilder.addNextIntent(resultIntent);
							PendingIntent resultPendingIntent =
							        stackBuilder.getPendingIntent(
							            0,
							            PendingIntent.FLAG_UPDATE_CURRENT
							        );
							mBuilder.setContentIntent(resultPendingIntent);
							NotificationManager mNotificationManager =
							    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
							// mId allows you to update the notification later on.
							mNotificationManager.notify(1, mBuilder.build());
						}
					}
				}
			}

		}).start();

	}

}
