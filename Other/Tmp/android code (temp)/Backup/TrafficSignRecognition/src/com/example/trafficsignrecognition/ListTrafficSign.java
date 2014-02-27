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
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

public class ListTrafficSign extends Activity {

	ListTrafficArrayAdapter myArray;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub
		Intent intent = getIntent();
		this.setTitle(intent.getStringExtra("catName"));
		setContentView(R.layout.activity_listtraffic);
		System.out.println("vao dc activity moi");
		
		ArrayList<vo.TrafficSign> arr = null;
		try {
			arr = (ArrayList<vo.TrafficSign>) ConvertUtil.bytes2Object(intent
					.getByteArrayExtra("trafficList"));
			System.out.println("Size ben nhan: " + arr.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*********************/
		final ListView lv = (ListView) findViewById(R.id.listTraffic);
		myArray = new ListTrafficArrayAdapter(this, R.layout.list_row, arr);
		lv.setAdapter(myArray);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				// vo.TrafficSign temView1 = (vo.TrafficSign)
				// lv.getItemAtPosition(position);
				// TextView trafficName1 =
				// (TextView)temView1.findViewById(R.id.trafficName);
				// String trafficName1 = temView1.getTrafficID();
				View temView2 = myArray.getView(position, null, lv);
				TextView trafficName = (TextView) temView2
						.findViewById(R.id.trafficName);
				TextView trafficID = (TextView) temView2
						.findViewById(R.id.trafficID);
				// String tmp = trafficName1.getText().toString();

				// int size = lv.getChildCount();
				// View tempView = lv.getChildAt(position);
				// System.out.println("vi tri ne : " + position);
				// TextView trafficName =
				// (TextView)tempView.findViewById(R.id.trafficName);
				// String tmp1 = trafficName1.getText().toString();
				// TextView trafficID =
				// (TextView)tempView.findViewById(R.id.trafficID);
				// parse json array
				String url = "http://192.168.43.56:8080/DemoRESTWS/REST/Webservice/ViewDetail?id="
						+ trafficID.getText();
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
						String title = "";
						for (int i = 0; i < objResult.length(); i++) {
							vo.TrafficSign traffic = new vo.TrafficSign();

							try {
								traffic.setTrafficName(objResult.getJSONObject(
										i).getString("name"));
								traffic.setTrafficInfo(objResult.getJSONObject(
										i).getString("information"));
								traffic.setTrafficImage("cam_di_nguoc_chieu");
								traffic.setTrafficPenaltyFee(objResult
										.getJSONObject(i).getString(
												"penaltyfee"));
								title = objResult.getJSONObject(i).getString(
										"name");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							arr.add(traffic);
						}

						System.out.println("truoc khi tao intent");
						Intent nextScreen = new Intent(getApplicationContext(),
								TracfficSignDetails.class);
						nextScreen.putExtra("trafficName", title);
						// Bundle bundle = new Bundle();
						// bundle.put
						try {
							byte[] dataBytes = ConvertUtil.object2Bytes(arr);
							System.out.println("Convert thanh cong");
							nextScreen.putExtra("trafficDetails", dataBytes);
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
				//

			}
		});
	}

}
