package com.example.trafficsignrecognition;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.ultils.ConvertUtil;
import com.example.ultils.JSONArrayParser;
import com.example.ultils.MyInterface.IAsyncFetchListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

public class Category extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		Intent intent = getIntent();
		ArrayList<vo.Category> arr = null;
		try {
			arr = (ArrayList<vo.Category>) ConvertUtil.bytes2Object(intent
					.getByteArrayExtra("catList"));
			System.out.println("Size: " + arr.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final ListView lv = (ListView) findViewById(R.id.listCategory);
		MyArrayAdapter myArray = new MyArrayAdapter(this, R.layout.list_rowcat,
				arr);
		lv.setAdapter(myArray);
		//
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				/**************/
				View tempView = lv.getChildAt(position);
				TextView textview = (TextView) tempView
						.findViewById(R.id.trafficName);
				TextView catID = (TextView) tempView
						.findViewById(R.id.trafficID);
				Intent nextScreen = new Intent(getApplicationContext(),
						ListTrafficSign.class);

				nextScreen.putExtra("catName", textview.getText());
				/*************/
				// parse json array
				String url = "http://192.168.43.56:8080/DemoRESTWS/REST/Webservice/Search?id="
						+ catID.getText();
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
						ArrayList<vo.TrafficSign> arr = new ArrayList<vo.TrafficSign>();
						System.out.println("Length result traffic: "
								+ objResult.length());
						for (int i = 0; i < objResult.length(); i++) {
							vo.TrafficSign traffic = new vo.TrafficSign();

							try {
								traffic.setTrafficID(objResult.getJSONObject(i)
										.getString("trafficID"));
								traffic.setTrafficName(objResult.getJSONObject(
										i).getString("name"));
								traffic.setTrafficImage("cam_di_nguoc_chieu");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							arr.add(traffic);
						}

						System.out.println("truoc khi tao intent");
						Intent nextScreen = new Intent(getApplicationContext(),
								ListTrafficSign.class);
						// Bundle bundle = new Bundle();
						// bundle.put
						try {
							byte[] dataBytes = ConvertUtil.object2Bytes(arr);
							System.out.println("Convert thanh cong");
							nextScreen.putExtra("trafficList", dataBytes);
							System.out.println("Put thanh cong");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// nextScreen.putExtra("catList", arr);
						System.out.println("sau khi tao intent");

						nextScreen.putExtra("catName", "Danh sach bien bao");
						startActivity(nextScreen);
					}
				});
				test.execute(new String[] { url });
				/*************/
				// View tempView = lv.getChildAt(position);
				// TextView textview = (TextView) tempView
				// .findViewById(R.id.trafficName);
				// TextView catID = (TextView) tempView
				// .findViewById(R.id.trafficID);
				// Intent nextScreen = new Intent(getApplicationContext(),
				// ListTrafficSign.class);

			}
		});
		//
		ImageButton btnSearch = (ImageButton) findViewById(R.id.searchButton);
		btnSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View V) {
				// TODO Auto-generated method stub
				// parse json array
				EditText searchKey = (EditText) findViewById(R.id.searchTraffic);
				String url = "http://192.168.43.56:8080/DemoRESTWS/REST/Webservice/SearchbyName?name="
						+ searchKey.getText();
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
						ArrayList<vo.TrafficSign> arr = new ArrayList<vo.TrafficSign>();
						System.out.println("Length result traffic: "
								+ objResult.length());
						for (int i = 0; i < objResult.length(); i++) {
							vo.TrafficSign traffic = new vo.TrafficSign();

							try {
								traffic.setTrafficID(objResult.getJSONObject(i)
										.getString("trafficID"));
								traffic.setTrafficName(objResult.getJSONObject(
										i).getString("name"));
								traffic.setTrafficImage("cam_di_nguoc_chieu");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							arr.add(traffic);
						}

						System.out.println("truoc khi tao intent");
						Intent nextScreen = new Intent(getApplicationContext(),
								ListTrafficSign.class);
						// Bundle bundle = new Bundle();
						// bundle.put
						try {
							byte[] dataBytes = ConvertUtil.object2Bytes(arr);
							System.out.println("Convert thanh cong");
							nextScreen.putExtra("trafficList", dataBytes);
							System.out.println("Put thanh cong");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// nextScreen.putExtra("catList", arr);
						System.out.println("sau khi tao intent");

						nextScreen.putExtra("catName", "Kết quả tìm kiếm");
						startActivity(nextScreen);
					}
				});
				test.execute(new String[] { url });
			}
		});
		// TODO Auto-generated method stub
	}

}
