package com.trafficsign.ultils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.reflect.TypeToken;
import com.trafficsign.activity.ListResultActivity;
import com.trafficsign.activity.MainActivity;
import com.trafficsign.activity.R;
import com.trafficsign.json.FavoriteJSON;
import com.trafficsign.json.ResultDB;
import com.trafficsign.json.ResultInput;
import com.trafficsign.json.ResultJSON;
import com.trafficsign.json.ResultShortJSON;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.json.TrafficInfoShortJSON;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class SyncUtil {
	int networkFlag;
	private NotificationCompat.Builder mBuilder = null;
	private NotificationManager mNotifyMgr = null;
	private int mNotificationId = 101;
	public int[] sync_image_loader = { R.drawable.stat_notify_sync_anim1,
			R.drawable.stat_notify_sync_anim2,
			R.drawable.stat_notify_sync_anim3,
			R.drawable.stat_notify_sync_anim4,
			R.drawable.stat_notify_sync_anim5,
			R.drawable.stat_notify_sync_anim6,
			R.drawable.stat_notify_sync_anim7,
			R.drawable.stat_notify_sync_anim8,
			R.drawable.stat_notify_sync_anim9,
			R.drawable.stat_notify_sync_anim10 };
	private int current_sync_image = 0;
	private Thread notifiThread = null;

	public void syncUserData(final Context context) {
		notifiThread = new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(300);
						if (current_sync_image > 9) {
							current_sync_image = 0;
						}
						mBuilder = new NotificationCompat.Builder(context)
								.setSmallIcon(
										sync_image_loader[current_sync_image++])
								.setContentTitle("Đồng bộ dữ liệu")
								.setContentText("Đang đồng bộ...");
						mBuilder.setAutoCancel(false);
						mNotifyMgr = (NotificationManager) context
								.getSystemService(context.NOTIFICATION_SERVICE);
						mNotifyMgr.notify(mNotificationId, mBuilder.build());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		// TODO Auto-generated method stub
		final SharedPreferences pref1 = context.getSharedPreferences(
				Properties.SHARE_PREFERENCE_LOGIN, Context.MODE_PRIVATE);
		final SharedPreferences pref2 = context.getSharedPreferences(
				Properties.SHARE_PREFERENCE_SETTING, Context.MODE_PRIVATE);
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
						notifiThread.start();
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
								resultJson = Helper.fromJson(jsonString,
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
								/*
								 * check if there are traffic sign in result do
								 * not have in DB
								 */
								ArrayList<ResultInput> listTraffic = resultJson
										.getListTraffic();
								if (listTraffic != null
										&& listTraffic.size() > 0) {
									for (int j = 0; j < listTraffic.size(); j++) {
										final String urlGetTrafficDetail = GlobalValue
												.getServiceAddress()
												+ Properties.TRAFFIC_TRAFFIC_VIEW
												+ "?id=";
										if (listTraffic.get(j).getTrafficID() != null
												&& DBUtil
														.checkTraffic(listTraffic
																.get(j)
																.getTrafficID()) == false) {
											String urlGetTrafficDetailFull = urlGetTrafficDetail
													+ listTraffic.get(j)
															.getTrafficID();
											// get traffic detail from service
											// and
											// parse json
											// TrafficInfoJson
											String trafficJSON = HttpUtil
													.get(urlGetTrafficDetailFull);
											TrafficInfoJSON trafficInfoJSON = new TrafficInfoJSON();
											trafficInfoJSON = Helper.fromJson(
													trafficJSON,
													TrafficInfoJSON.class);
											// add traffic to DB
											if (DBUtil
													.checkTraffic(trafficInfoJSON
															.getTrafficID()) == false) {
												DBUtil.insertTraffic(trafficInfoJSON);

											}
											String savePath = GlobalValue
													.getAppFolder()
													+ Properties.MAIN_IMAGE_FOLDER
													+ trafficInfoJSON
															.getTrafficID()
													+ ".jpg";
											File image = new File(savePath);
											if (!image.exists()) {
												String imageLink = GlobalValue
														.getServiceAddress()
														+ trafficInfoJSON
																.getImage();
												if (HttpUtil.downloadImage(
														imageLink, savePath)) {
													Log.e("DB Image", savePath);
												}

											}
										}
									}
								}

								/* NOTIFICATION */
								if (notiStatus == true) {
									NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
											context)
											.setSmallIcon(
													R.drawable.ic_launcher)
											.setContentTitle(
													"Kết quả nhận dạng")
											.setContentText(
													Helper.dateToString(listResult
															.get(i)
															.getCreateDate()));
									mBuilder.setDefaults(-1);
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
										String dateTimeString = Helper
												.toJson(listFavorite.get(i)
														.getModifyDate());

										parameter.add(new BasicNameValuePair(
												"modifyDate", dateTimeString));
										String respones = "";
										respones = HttpUtil.post(
												urlAddFavorite, parameter);
										Log.e("SyncUtil", respones);
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
										String dateTimeString = Helper
												.toJson(listFavorite.get(i)
														.getModifyDate());

										urlDeleteFavorite += URLEncoder
												.encode(dateTimeString);
										String respones = "";
										respones = HttpUtil
												.get(urlDeleteFavorite);
										Log.e("SyncUtil", respones);
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
							Type typeListFavorite = new TypeToken<ArrayList<TrafficInfoShortJSON>>() {
							}.getType();
							ArrayList<TrafficInfoShortJSON> newListFavorite = Helper
									.fromJson(favoriteResponse,
											typeListFavorite);
							// remove favorite list
							if (newListFavorite != null) {
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
							ArrayList<ResultShortJSON> listResultShortJSON = Helper
									.fromJson(listResponse, typeListHistory);
							
							if (listResultShortJSON != null) {
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
										ResultJSON historyDetail = Helper
												.fromJson(viewHistoryResponse,
														ResultJSON.class);
										if (historyDetail != null) {
											Log.e("SyncUtil",
													"Syc history: "
															+ historyDetail
																	.getResultID());
											// download uploaded image if image
											// is
											// not exist
											String imagePath = GlobalValue
													.getAppFolder()
													+ Properties.SAVE_IMAGE_FOLDER
													+ historyDetail
															.getResultID()
													+ ".jpg";
											File image = new File(imagePath);
											if (!image.exists()) {
												String imageUrl = GlobalValue
														.getServiceAddress()
														+ historyDetail
																.getUploadedImage();
												HttpUtil.downloadImage(
														imageUrl, imagePath);
											}
											// save result to db

											ResultDB resultDB = new ResultDB();
											resultDB.setCreateDate(historyDetail
													.getCreateDate());
											resultDB.setCreator(historyDetail
													.getCreator());
											resultDB.setLocate(Helper
													.toJson(historyDetail
															.getListTraffic()));
											resultDB.setResultID(historyDetail
													.getResultID());
											resultDB.setUploadedImage(imagePath);
											DBUtil.addResult(resultDB, userID);
										}
									}// end if
								}// end for

								// listHistory = DBUtil.listAllHistory();
								for (int i = 0; i < listHistory.size(); i++) {
									boolean flagDeletedOnServer = true;
									for (int j = 0; j < listResultShortJSON
											.size(); j++) {
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
							}//end if list null
							
							// End sync history
							/* End Sync process */
						}
						try {
							notifiThread.interrupt();
							while (notifiThread.isAlive()) {
								Thread.sleep(300);
							}
							mNotifyMgr.cancel(mNotificationId);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						GlobalValue.isUploading = false;
					}// end if isUploading
				}// end if isAccessServer
			}

		}).start();
	}
}
