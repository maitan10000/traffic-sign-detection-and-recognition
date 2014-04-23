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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.trafficsign.json.FavoriteJSON;
import com.trafficsign.json.ResultDB;
import com.trafficsign.json.ResultJSON;
import com.trafficsign.json.ResultShortJSON;
import com.trafficsign.json.TrafficInfoJSON;
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
	int networkFlag;

	@Override
	public void onReceive(final Context context, final Intent intent) {
		// TODO Auto-generated method stub
		final SharedPreferences pref1 = context.getSharedPreferences(
				Properties.SHARE_PREFERENCE_LOGIN, Context.MODE_PRIVATE);
		final SharedPreferences pref2 = context.getSharedPreferences(
				Properties.SHARE_PREFERENCE_LOGIN, Context.MODE_PRIVATE);
		final String userID = pref1.getString(
				Properties.SHARE_PREFERENCE__KEY_USER, "");
		final boolean notiStatus = pref2.getBoolean(
				Properties.SHARE_PREFERENCE__KEY_NOTI, true);
		boolean wifiStatus = pref2.getBoolean(
				Properties.SHARE_PREFERENCE_KEY_WIFI, true);
		networkFlag = 1;
		if (wifiStatus == false) {
			networkFlag = 0;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// if access to server ok and meet network setting
				if (NetUtil.networkState(context) > networkFlag) {
					if (GlobalValue.isUploading == false) {
						GlobalValue.isUploading = true;
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
								if (listResult.get(i).getLocate() != null
										&& "".equals(listResult.get(i)
												.getLocate()) == false) {
									parameters.add(new BasicNameValuePair(
											"listLocate", listResult.get(i)
													.getLocate()));
								}

								// upload and parse result
								String jsonString = "";
								jsonString = UploadUtils.uploadFile(listResult
										.get(i).getUploadedImage(),
										upLoadServerUri, parameters);
								ResultJSON resultJson = new ResultJSON();
								Gson gson = new GsonBuilder().setDateFormat(
										DateFormat.FULL, DateFormat.FULL)
										.create();
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
								if (notiStatus == true) {
									NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
											context)
											.setSmallIcon(
													R.drawable.ic_launcher)
											.setContentTitle(
													"Kết quả nhận diện")
											.setContentText(
													listResult.get(i)
															.getCreateDate()
															.toString());
									Intent resultIntent = new Intent(context,
											ListResultActivity.class);
									resultIntent.putExtra("resultJson",
											dataBytes);
									resultIntent.putExtra("imagePath",
											listResult.get(i)
													.getUploadedImage());
									// The stack builder object will contain an
									// artificial back stack for the
									// started Activity.
									// This ensures that navigating backward
									// from
									// the Activity leads out of
									// your application to the Home screen.
									TaskStackBuilder stackBuilder = TaskStackBuilder
											.create(context);
									// Adds the back stack for the Intent (but
									// not
									// the Intent itself)
									stackBuilder
											.addParentStack(MainActivity.class);
									// Adds the Intent that starts the Activity
									// to
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
									mNotificationManager.notify(i,
											mBuilder.build());
								}

							}// end for
						}
						/* end auto search */
						/* Sync process */
						Gson gson = new GsonBuilder().setDateFormat(
								DateFormat.FULL, DateFormat.FULL).create();
						if ("".equals(userID) == false) {
							// Sync favorite
							ArrayList<FavoriteJSON> listFavorite = new ArrayList<FavoriteJSON>();
							listFavorite = DBUtil.listAllFavorite();
							if (listFavorite.size() > 0) {

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
												"creator", userID));
										parameter
												.add(new BasicNameValuePair(
														"trafficID",
														listFavorite.get(i)
																.getTrafficID()));
										String dateTimeString = gson
												.toJson(listFavorite.get(i)
														.getModifyDate());

										parameter.add(new BasicNameValuePair(
												"modifyDate", dateTimeString));
										String respones = "";
										respones = HttpUtil.post(
												urlAddFavorite, parameter);
										Log.e("sync", respones);
									} else { // if favorite is deActive, excute
												// deleteClone to service
										// url for delete favorite
										String urlDeleteFavorite = GlobalValue
												.getServiceAddress()
												+ Properties.MANAGE_FAVORITE_DELETE
												+ "?creator="
												+ userID
												+ "&trafficID=";
										urlDeleteFavorite += listFavorite
												.get(i).getTrafficID();
										urlDeleteFavorite += "&modifyDate=";
										// parse datetime to json
										String dateTimeString = gson
												.toJson(listFavorite.get(i)
														.getModifyDate());

										urlDeleteFavorite += URLEncoder
												.encode(dateTimeString);
										String respones = "";
										respones = HttpUtil
												.get(urlDeleteFavorite);
										Log.e("sync", respones);
									}
								}
							}
							// receive new favorite list from service
							// URL for service
							String urlListFavorite = GlobalValue
									.getServiceAddress()
									+ Properties.MANAGE_FAVORITE_LIST
									+ "?creator=" + userID;
							// get all favorite from service and parse json
							// to
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
								DBUtil.removeAllFavorite();
								for (int i = 0; i < newListFavorite.size(); i++) {
									DBUtil.addFavorite(newListFavorite.get(i),
											userID);
								}
							}

							// End sync favorite

							// Sync history
							// listHistory from mobile DB
							ArrayList<ResultDB> listHistory = DBUtil
									.listAllHistory();
							for (int i = 0; i < listHistory.size(); i++) {
								if (listHistory.get(i).isActive() == false) {
									String urlDelete = GlobalValue
											.getServiceAddress()
											+ Properties.TRAFFIC_HISTORY_DELETE
											+ "?id=";
									urlDelete += listHistory.get(i)
											.getResultID();
									String response = HttpUtil.get(urlDelete);
									if ("Success".equals(response.trim())) {
										DBUtil.deleteResult(listHistory.get(i)
												.getResultID());
										listHistory.remove(i);
										--i;
									}
								}
							}

							String urlListHistory = GlobalValue
									.getServiceAddress()
									+ Properties.TRAFFIC_HISTORY_LIST
									+ "?creator=" + userID;
							String listResponse = HttpUtil.get(urlListHistory);
							Type typeListHistory = new TypeToken<ArrayList<ResultShortJSON>>() {
							}.getType();
//							Gson gson = new GsonBuilder().setDateFormat(
//									DateFormat.FULL, DateFormat.FULL).create();
							ArrayList<ResultShortJSON> listResultShortJSON = gson
									.fromJson(listResponse, typeListHistory);

							for (int i = 0; i < listResultShortJSON.size(); i++) {
								boolean flagAddNew = true;
								for (int j = 0; j < listHistory.size(); j++) {
									if (listHistory.get(j).getResultID() == listResultShortJSON
											.get(i).getResultID()) {
										flagAddNew = false;
									}
								}

								if (flagAddNew == true) {
									String urlViewHistory = GlobalValue
											.getServiceAddress()
											+ Properties.TRAFFIC_HISTORY_VIEW
											+ "?id=";
									urlViewHistory += listResultShortJSON
											.get(i).getResultID();
									String viewHistoryResponse = HttpUtil
											.get(urlViewHistory);
									ResultJSON historyDetail = gson.fromJson(
											viewHistoryResponse,
											ResultJSON.class);
									if (historyDetail != null) {
										Log.e("syncHistory",
												historyDetail.getResultID()
														+ "");
										// download uploaded image if image is
										// not exist
										String imagePath = GlobalValue
												.getAppFolder()
												+ Properties.SAVE_IMAGE_FOLDER
												+ historyDetail.getResultID()
												+ ".jpg";
										File image = new File(imagePath);
										if (!image.exists()) {
											String imageUrl = GlobalValue
													.getServiceAddress()
													+ historyDetail
															.getUploadedImage();
											HttpUtil.downloadImage(imageUrl,
													imagePath);
										}
										// save result to db

										ResultDB resultDB = new ResultDB();
										resultDB.setCreateDate(historyDetail
												.getCreateDate());
										resultDB.setCreator(historyDetail
												.getCreator());
										resultDB.setLocate(gson
												.toJson(historyDetail
														.getListTraffic()));
										resultDB.setResultID(historyDetail
												.getResultID());
										resultDB.setUploadedImage(imagePath);
										DBUtil.addResult(resultDB, userID);
									}
								}// end if
							}

							// listHistory = DBUtil.listAllHistory();
							for (int i = 0; i < listHistory.size(); i++) {
								boolean flagDeletedOnServer = true;
								for (int j = 0; j < listResultShortJSON.size(); j++) {
									if (listHistory.get(i).getResultID() == listResultShortJSON
											.get(j).getResultID()) {
										flagDeletedOnServer = false;
									}
								}
								if (flagDeletedOnServer == true) {
									DBUtil.deleteResult(listHistory.get(i)
											.getResultID());
								}
							}

							// End sync history
							/* End Sync process */
						}

						GlobalValue.isUploading = false;
					}// end if isUploading
				}// end if isAccessServer
			}

		}).start();
	}

	// update traffic sign from server
	public static void updateTraffic() {
		Gson gson = new Gson();
		// URL for service
		String urlGetListTraffic = Properties.serviceIp
				+ Properties.TRAFFIC_SEARCH_MANUAL + "?name=";
		String urlGetTrafficDetail = Properties.serviceIp
				+ Properties.TRAFFIC_TRAFFIC_VIEW + "?id=";
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
			int dbReturn; // variable to know db return
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
				dbReturn = DBUtil.updateTraffic(trafficInfoJSON);
				// download image if not exist
				String savePath = GlobalValue.getAppFolder()
						+ Properties.MAIN_IMAGE_FOLDER
						+ trafficInfoJSON.getTrafficID() + ".jpg";
				File image = new File(savePath);
				if (!image.exists()) {
					String imageLink = Properties.serviceIp
							+ trafficInfoJSON.getImage();
					HttpUtil.downloadImage(imageLink, savePath);
				}

				Log.e("DB", dbReturn + "");
			}
		}
	}
}
