package com.example.trafficsignrecognition;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ultils.ConvertUtil;
import com.example.ultils.JSONArrayParser;
import com.example.ultils.JSONParser;
import com.example.ultils.MyInterface.IAsyncFetchListener;
import com.example.ultils.Test;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// set event for manual search button
		ImageButton imgBtnManualSearch = (ImageButton) findViewById(R.id.imageButtonManualSearch);

		imgBtnManualSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// parse json array
				String url = "http://192.168.43.56:8080/DemoRESTWS/REST/Webservice/GetResult";
				JSONArrayParser test = new JSONArrayParser();
				test.setListener(new IAsyncFetchListener() {

					@Override
					public void onError(String errorMessage) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onComplete(JSONArray objResult) {
						// TODO Auto-generated method stub
						System.out.println("Length result: "
								+ objResult.length());
						// get data from json array to list
						ArrayList<vo.Category> arr = new ArrayList<vo.Category>();
						for (int i = 0; i < objResult.length(); i++) {
							vo.Category cat = new vo.Category();

							try {
								cat.setCatImage("trafficicon");
								cat.setCatName(objResult.getJSONObject(i)
										.getString("categoryName"));
								cat.setId(objResult.getJSONObject(i).getString(
										"categoryID"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							arr.add(cat);
						}
						System.out.println("truoc khi tao intent");
						Intent nextScreen = new Intent(getApplicationContext(),
								Category.class);
						// Bundle bundle = new Bundle();
						// bundle.put
						try {
							byte[] dataBytes = ConvertUtil.object2Bytes(arr);
							System.out.println("Convert thanh cong");
							nextScreen.putExtra("catList", dataBytes);
							System.out.println("Put thanh cong");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// nextScreen.putExtra("catList", arr);
						System.out.println("sau khi tao intent");
						startActivity(nextScreen);
					}
				});
				test.execute(new String[] { url });
			}
		});
		// set event onclick for auto search button
		ImageButton imgBtnAutoSearch = (ImageButton) findViewById(R.id.imageButtonAutoSearch);
		imgBtnAutoSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent nextScreen = new Intent(getApplicationContext(),
						CameraActivity.class);
				startActivity(nextScreen);
			}
		});
		// set event onclick for auto search button
		ImageButton imgBtnHistory = (ImageButton) findViewById(R.id.imageButtonHistory);
		imgBtnHistory.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent nextScreen = new Intent(getApplicationContext(),
						ListResult.class);
				startActivity(nextScreen);
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
