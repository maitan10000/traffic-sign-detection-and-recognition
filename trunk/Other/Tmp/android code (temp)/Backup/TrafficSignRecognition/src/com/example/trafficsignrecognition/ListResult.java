package com.example.trafficsignrecognition;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import vo.ResultInput;
import vo.TrafficSign;

import com.example.ultils.ConvertUtil;
import com.example.ultils.ImageUtils;
import com.example.ultils.JSONArrayParser;
import com.example.ultils.MyInterface.IAsyncFetchListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListResult extends Activity {

	ListResultArrayAdapter myArray;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub
		setContentView(R.layout.activity_result);
		Intent intent = getIntent();
		// Data fix cung de test
		ArrayList<vo.ResultInput> arr = new ArrayList<ResultInput>();
		ResultInput test = new ResultInput();
		test.setTrafficID("ID001");
		test.setTrafficImage("cam_di_nguoc_chieu");
		test.setTrafficName("cam di nguoc chieu");
		arr.add(test);
		arr.add(test);
		arr.add(test);
		arr.add(test);
		arr.add(test);
		arr.add(test);
		arr.add(test);
		arr.add(test);
		//
		// Get list from extra
		ArrayList<vo.ResultInput> arr2 = null;
		try {
			arr2 = (ArrayList<vo.ResultInput>) ConvertUtil.bytes2Object(intent
					.getByteArrayExtra("listResult"));
			System.out.println("Size: " + arr2.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/////
		final ListView lv = (ListView) findViewById(R.id.listResult);
		myArray = new ListResultArrayAdapter(this, R.layout.list_row, arr);
		lv.setAdapter(myArray);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				View temView2 = myArray.getView(position, null, lv);
				TextView trafficName = (TextView) temView2
						.findViewById(R.id.trafficName);
				TextView trafficID = (TextView) temView2
						.findViewById(R.id.trafficID);
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
		ImageView resultImage = (ImageView) findViewById(R.id.imageResult);
		String imagePath = "/mnt/sdcard/test.jpg";
		Bitmap image = ImageUtils.drawImage(imagePath, arr2);
		resultImage.setImageBitmap(image);
	}

}
