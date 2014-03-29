package com.trafficsign.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import static com.trafficsign.ultils.Properties.serviceIp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trafficsign.ultils.Properties;
import com.trafficsign.activity.R;
import com.trafficsign.json.CategoryJSON;
import com.trafficsign.json.FavoriteJSON;
import com.trafficsign.json.TrafficInfoShortJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.HttpAsyncUtil;
import com.trafficsign.ultils.HttpDatabaseUtil;
import com.trafficsign.ultils.MyInterface.IAsyncHttpImageListener;
import com.trafficsign.ultils.MyInterface.IAsyncHttpListener;
import com.trafficsign.ultils.NetUtil;
//import com.example.ultils.MyInterface.IAsyncFetchListener;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// init resource
		InputStream dbIS = getResources().openRawResource(R.raw.traffic_sign);
		InputStream settingIS = getResources().openRawResource(R.raw.setting);
		DBUtil.initResource(dbIS, settingIS, MainActivity.this);
		//
//		Toast.makeText(getApplicationContext(),
//				"Trang thai"+ NetUtil.networkState(this), Toast.LENGTH_SHORT).show();
		/* Set event for manual search button */
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
				// url for list favorite service
				String url = Properties.serviceIp
						+ Properties.MANAGE_FAVORITE_LIST + "?creator=";
				url += Properties.USER_NAME;
				//
				HttpAsyncUtil httpUtil = new HttpAsyncUtil(MainActivity.this);
				httpUtil.setHttpListener(new IAsyncHttpListener() {

					@Override
					public void onComplete(String respond) {
						// parse Json to Object FavoriteJSON
						try {
							/*
							 * Using trafficInfoShort, not user FavoriteJSON
							 * because they are the same, instead of using
							 * trafficInfoShort can reuse module
							 * listTrafficArrayAdapter
							 */
							ArrayList<TrafficInfoShortJSON> listFavoriteJSON = new ArrayList<TrafficInfoShortJSON>();
							Gson gson = new Gson();
							Type type = new TypeToken<ArrayList<TrafficInfoShortJSON>>() {
							}.getType();
							listFavoriteJSON = gson.fromJson(respond, type);
							Intent nextScreen = new Intent(
									getApplicationContext(),
									FavouriteActivity.class);
							byte[] datBytes = ConvertUtil
									.object2Bytes(listFavoriteJSON);
							nextScreen.putExtra("listFavorite", datBytes);
							startActivity(nextScreen);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
				httpUtil.setUrl(url);
				httpUtil.execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
