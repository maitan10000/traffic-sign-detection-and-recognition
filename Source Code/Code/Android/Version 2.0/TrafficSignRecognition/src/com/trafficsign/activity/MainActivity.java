package com.trafficsign.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;




import com.trafficsign.ultils.Properties;
import com.trafficsign.activity.R;
import com.trafficsign.json.CategoryJSON;
import com.trafficsign.json.FavoriteJSON;
import com.trafficsign.json.TrafficInfoShortJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.HttpAsyncUtil;
import com.trafficsign.ultils.HttpDatabaseUtil;
import com.trafficsign.ultils.HttpSyncUtil;
import com.trafficsign.ultils.MyInterface.IAsyncHttpImageListener;
import com.trafficsign.ultils.MyInterface.IAsyncHttpListener;
import com.trafficsign.ultils.NetUtil;
//import com.example.ultils.MyInterface.IAsyncFetchListener;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends Activity {
	String user = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("Hệ thống nhận dạng biển báo");
		final SharedPreferences prefLogin = getSharedPreferences(
				Properties.SHARE_PREFERENCE_LOGIN, MODE_PRIVATE);
		final SharedPreferences prefSetting = getSharedPreferences(
				Properties.SHARE_PREFERENCE_SETTING, MODE_PRIVATE);
		// init resource
		InputStream dbIS = getResources().openRawResource(R.raw.traffic_sign);
		InputStream settingIS = getResources().openRawResource(R.raw.setting);
		DBUtil.initResource(dbIS, settingIS, MainActivity.this);
		

		// get user

		user = prefLogin.getString(Properties.SHARE_PREFERENCE__KEY_USER, "");
		if ("".equals(user) == false) {
			// sync favorite and history
			SharedPreferences sharedPreferences = getSharedPreferences(
					Properties.SHARE_PREFERENCE_LOGIN, MODE_PRIVATE);
			boolean isSync = sharedPreferences.getBoolean("isSync", true);
			if (isSync == false) {
				HttpSyncUtil httpSyncUtil = new HttpSyncUtil(MainActivity.this);
				httpSyncUtil.setUser(this.user);
				httpSyncUtil.setHttpListener(new IAsyncHttpListener() {

					@Override
					public void onComplete(String respond) {
						// TODO Auto-generated method stub
						Editor editor = prefLogin.edit();
						editor.remove("isSync");
						editor.putBoolean("isSync", true);
						editor.commit();
					}
				});
				httpSyncUtil.execute();
			}
		}

		// Get and set event onclick for manual search button
		ImageButton imgBtnManualSearch = (ImageButton) findViewById(R.id.imageButtonManualSearch);
		imgBtnManualSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<CategoryJSON> listCategory = new ArrayList<CategoryJSON>();
				byte[] dataBytes;
				try {
					listCategory = DBUtil.getAllCategory();
					Intent nextScreen = new Intent(getApplicationContext(),
							CategoryActivity.class);
					dataBytes = ConvertUtil.object2Bytes(listCategory);
					nextScreen.putExtra("catList", dataBytes);
					startActivity(nextScreen);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		/* Set event onclick for auto search button */
		final ImageButton imgBtnAutoSearch = (ImageButton) findViewById(R.id.imageButtonAutoSearch);
		imgBtnAutoSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent nextScreen = new Intent(getApplicationContext(),
						CameraActivity.class);
				startActivity(nextScreen);

			}
		});
		/* Set event onclick for favorite button */
		ImageButton imgBtnFavorite = (ImageButton) findViewById(R.id.imageButtonFavorite);
		imgBtnFavorite.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ("".equals(user) == false) {
					Intent nextScreen = new Intent(getApplicationContext(),
							FavouriteActivity.class);
					startActivity(nextScreen);
				} else {
					Toast.makeText(getApplicationContext(),
							"Vui lòng đăng nhập để sử dụng chức năng này",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		/* Set event onclick for histoy button */
		ImageButton imgBtnHistory = (ImageButton) findViewById(R.id.imageButtonHistory);
		imgBtnHistory.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ("".equals(user) == false) {
					Intent nextScreen = new Intent(getApplicationContext(),
							HistoryActivity.class);
					startActivity(nextScreen);
				} else {
					Toast.makeText(getApplicationContext(),
							"Vui lòng đăng nhập để sử dụng chức năng này",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferences pref = getSharedPreferences(
				Properties.SHARE_PREFERENCE_LOGIN, MODE_PRIVATE);
		user = pref.getString(Properties.SHARE_PREFERENCE__KEY_USER, "");
		// set alarm receiver for update service
		SharedPreferences prefSetting = getSharedPreferences(
				Properties.SHARE_PREFERENCE_SETTING, MODE_PRIVATE);
				if ("".equals(prefSetting.getString(
						Properties.SHARE_PREFERENCE__KEY_TRAFFIC_UPDATE_ALARM, ""))) {
					Long time = prefSetting.getLong(Properties.SHARE_PREFERENCE__KEY_TRAFFIC_UPDATE_TIME, 30);
					// convert to days
					time = time * 24 * 60 * 60 * 1000;
					scheduleAlarm(time);
					Editor editor = prefSetting.edit();
					editor.putString(
							Properties.SHARE_PREFERENCE__KEY_TRAFFIC_UPDATE_ALARM, "ok");
					editor.commit();
				}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		// if user has been logged in

		if ("".equals(user) == false) {
			getMenuInflater().inflate(R.menu.main_login, menu);
			MenuItem item = menu.getItem(0);
			item.setTitle(user);
		} else {
			getMenuInflater().inflate(R.menu.main_not_login, menu);
		}
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.action_checklogin) {
			// user has been logged in

			Intent nextScreen = new Intent(getApplicationContext(),
					LoginActivity.class);
			finish();
			startActivity(nextScreen);

		} else if (item.getItemId() == R.id.action_checkLogout) {
			if ("".equals(user)) {
				Intent nextScreen = new Intent(getApplicationContext(),
						LoginActivity.class);
				finish();
				startActivity(nextScreen);
			} else {
				SharedPreferences pref = getSharedPreferences(
						Properties.SHARE_PREFERENCE_LOGIN, MODE_PRIVATE);
				Editor editor = pref.edit();
				editor.remove(Properties.SHARE_PREFERENCE__KEY_USER);
				editor.remove(Properties.SHARE_PREFERENCE__KEY_USER_SYNC);
				editor.putBoolean(Properties.SHARE_PREFERENCE__KEY_USER_SYNC,
						true);
				editor.commit();
				this.user = "";
				this.invalidateOptionsMenu();
			}

		} else if (item.getItemId() == R.id.action_settings) {
			Intent nextScreen = new Intent(getApplicationContext(),
					SettingActivity.class);
			startActivity(nextScreen);
		} else if (item.getItemId() == R.id.action_register) {
			Intent nextScreen = new Intent(getApplicationContext(),
					RegisterActivity.class);
			startActivity(nextScreen);
		} else if (item.getItemId() == R.id.action_update) {
			final SharedPreferences sharedPreferences = getSharedPreferences(
					Properties.SHARE_PREFERENCE_SETTING, 0);
			// if update process is not running
			if (sharedPreferences.getBoolean(
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
						// TODO Auto-generated method stub
						try {
							if (NetUtil.isAccessService() == true) {

								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(
												getApplicationContext(),
												"Bắt đầu quá trình cập nhật",
												Toast.LENGTH_SHORT).show();
									}
								});

								DBUtil.updateTrafficsign(getApplicationContext());
							} else {
								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(
												getApplicationContext(),
												"Không thể kết nối tới máy chủ, vui lòng kiểm tra lại",
												Toast.LENGTH_SHORT).show();
									}
								});
							}
						} finally {
							final Editor editor = sharedPreferences.edit();
							editor.putBoolean(
									Properties.SHARE_PREFERENCE__KEY_TRAFFIC_UPDATE_RUNNING,
									false);
							editor.commit();
						}

					}

				}).start();
			} else {
				Toast.makeText(
						getApplicationContext(),
						"Quá trình cập nhật đang chạy, vui lòng đợi trong giây lát",
						Toast.LENGTH_SHORT).show();
			}
		}
		return true;

	}

	public void scheduleAlarm(Long range) {
		Log.e("alarm changed", range / 24 / 60/ 60/1000 + "");
		Long time = System.currentTimeMillis() + range;
		Intent intentAlarm = new Intent(this, AlarmReceiver.class);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC, time, range, PendingIntent
				.getBroadcast(this, 1, intentAlarm,
						PendingIntent.FLAG_UPDATE_CURRENT));
	}

}
