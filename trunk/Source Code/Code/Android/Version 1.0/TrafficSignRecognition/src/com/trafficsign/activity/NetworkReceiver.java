package com.trafficsign.activity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.trafficsign.json.FavoriteJSON;
import com.trafficsign.json.ResultDB;
import com.trafficsign.json.ResultJSON;
import com.trafficsign.json.TrafficInfoShortJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.GlobalValue;
import com.trafficsign.ultils.HttpUtil;
import com.trafficsign.ultils.NetUtil;
import com.trafficsign.ultils.Properties;
import com.trafficsign.ultils.UploadUtils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {
		// TODO Auto-generated method stub
		final SharedPreferences pref = context.getSharedPreferences(
				Properties.SHARE_PREFERENCE_LOGIN, Context.MODE_PRIVATE);
		final String user = pref.getString("user", "notLogin");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// if access to server ok

				if (NetUtil.networkState(context) > Properties.INTERNET_SETTING) {
					if (GlobalValue.isUploading == false) {
						GlobalValue.isUploading = true;
						/* Sync process */
						ArrayList<FavoriteJSON> listFavorite = new ArrayList<FavoriteJSON>();
						listFavorite = DBUtil.listAllFavorite();
						if (listFavorite.size() > 0) {
							Gson gson = new GsonBuilder().setDateFormat(
									DateFormat.FULL, DateFormat.FULL).create();
							for (int i = 0; i < listFavorite.size(); i++) {
								// if favorite is active, excute addClone to
								// service
								if (listFavorite.get(i).isActive() == true) {

									// URL for service add favorite
									String urlAddFavorite = GlobalValue
											.getServiceAddress()
											+ Properties.MANAGE_FAVORITE_ADD;
									ArrayList<NameValuePair> parameter = new ArrayList<NameValuePair>();
									parameter.add(new BasicNameValuePair(
											"creator", user));
									parameter.add(new BasicNameValuePair(
											"trafficID", listFavorite.get(i)
													.getTrafficID()));
									String dateTimeString = gson
											.toJson(listFavorite.get(i)
													.getModifyDate());

									parameter.add(new BasicNameValuePair(
											"modifyDate", dateTimeString));
									String respones = "";
									respones = HttpUtil.post(urlAddFavorite,
											parameter);
									Log.e("sync", respones);
								} else { // if favorite is deActive, excute
											// deleteClone to service
									// url for delete favorite
									String urlDeleteFavorite = GlobalValue
											.getServiceAddress()
											+ Properties.MANAGE_FAVORITE_DELETE
											+ "?creator="
											+ user
											+ "&trafficID=";
									urlDeleteFavorite += listFavorite.get(i)
											.getTrafficID();
									urlDeleteFavorite += "&modifyDate=";
									// parse datetime to json
									String dateTimeString = gson
											.toJson(listFavorite.get(i)
													.getModifyDate());
									
									urlDeleteFavorite += URLEncoder.encode(dateTimeString);
									String respones = "";
									respones = HttpUtil.get(urlDeleteFavorite);
									Log.e("sync", respones);
								}
							}
							// receive new favorite list from service
							// URL for service
							String urlListFavorite = GlobalValue.getServiceAddress()
									+ Properties.MANAGE_FAVORITE_LIST
									+ "?creator=" + user;
							// get all favorite from service and parse json to
							// list Trafficinfoshort
							String favoriteResponse = HttpUtil
									.get(urlListFavorite);
							ArrayList<TrafficInfoShortJSON> newListFavorite = new ArrayList<TrafficInfoShortJSON>();
							Type typeListFavorite = new TypeToken<ArrayList<TrafficInfoShortJSON>>() {
							}.getType();
							newListFavorite = gson.fromJson(favoriteResponse,
									typeListFavorite);
							// remove favorite list

							if (newListFavorite.size() > 0) {
								DBUtil.removeFavorite();
								for (int i = 0; i < newListFavorite.size(); i++) {
									DBUtil.addFavorite(newListFavorite.get(i),
											user);
								}
							}
						}

						/* End Sync process */
						/* Auto search */
						final String upLoadServerUri = GlobalValue
								.getServiceAddress()
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
								parameters.add(new BasicNameValuePair(
										"listLocate", listResult.get(i)
												.getLocate()));

								// upload and parse result
								String jsonString = UploadUtils.uploadFile(
										listResult.get(i).getUploadedImage(),
										upLoadServerUri, parameters);
								ResultJSON resultJson = new ResultJSON();
								Gson gson = new Gson();
								resultJson = gson.fromJson(jsonString,
										ResultJSON.class);
								byte[] dataBytes = null;
								try {
									dataBytes = ConvertUtil
											.object2Bytes(resultJson);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Log.e("search", jsonString);
								if (jsonString != null
										|| jsonString.length() > 5) {
									DBUtil.deleteSavedResult(listResult.get(i)
											.getUploadedImage());
								}

								/* NOTIFICATION */
								NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
										context)
										.setSmallIcon(R.drawable.ic_launcher)
										.setContentTitle("Kết quả nhận diện")
										.setContentText(
												listResult.get(i)
														.getCreateDate()
														.toString());
								Intent resultIntent = new Intent(context,
										ListResultActivity.class);
								resultIntent.putExtra("resultJson", dataBytes);
								resultIntent.putExtra("imagePath", listResult
										.get(i).getUploadedImage());
								// The stack builder object will contain an
								// artificial back stack for the
								// started Activity.
								// This ensures that navigating backward from
								// the Activity leads out of
								// your application to the Home screen.
								TaskStackBuilder stackBuilder = TaskStackBuilder
										.create(context);
								// Adds the back stack for the Intent (but not
								// the Intent itself)
								stackBuilder.addParentStack(MainActivity.class);
								// Adds the Intent that starts the Activity to
								// the top of the stack
								stackBuilder.addNextIntent(resultIntent);
								PendingIntent resultPendingIntent = stackBuilder
										.getPendingIntent(
												i,
												PendingIntent.FLAG_UPDATE_CURRENT);
								mBuilder.setContentIntent(resultPendingIntent);
								mBuilder.setAutoCancel(true);
								NotificationManager mNotificationManager = (NotificationManager) context
										.getSystemService(context.NOTIFICATION_SERVICE);
								// mId allows you to update the notification
								// later on.
								mNotificationManager.notify(i, mBuilder.build());

							}// end for
						}
						/* end auto search */
						GlobalValue.isUploading = false;
					}// end if isUploading
				}// end if isAccessServer
			}

		}).start();
	}
}
