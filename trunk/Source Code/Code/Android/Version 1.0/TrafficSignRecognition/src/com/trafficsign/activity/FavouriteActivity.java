package com.trafficsign.activity;

import static com.trafficsign.ultils.Properties.serviceIp;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.trafficsign.activity.R;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.json.TrafficInfoShortJSON;
import com.trafficsign.ultils.CommonUtil;
import com.trafficsign.ultils.HttpImageUtils;
import com.trafficsign.ultils.HttpAsyncUtil;
import com.trafficsign.ultils.Properties;
import com.trafficsign.ultils.MyInterface.IAsyncHttpImageListener;
import com.trafficsign.ultils.MyInterface.IAsyncHttpListener;

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

public class FavouriteActivity extends Activity {

	ListTrafficArrayAdapter myArray;
	ArrayList<TrafficInfoShortJSON> listFavorite = null;
	static ListView lv = null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourite);
		Intent intent = getIntent();
		this.setTitle("Danh sách đã lưu");


		try {
			listFavorite = (ArrayList<TrafficInfoShortJSON>) CommonUtil
					.bytes2Object(intent.getByteArrayExtra("listFavorite"));
			if (listFavorite == null) {
				listFavorite = new ArrayList<TrafficInfoShortJSON>();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*********************/
		lv = (ListView) findViewById(R.id.listFavorite);
		myArray = new ListTrafficArrayAdapter(this, R.layout.list_row,
				listFavorite);
		lv.setAdapter(myArray);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				View temView2 = myArray.getView(position, null, lv);
				TextView trafficName = (TextView) temView2
						.findViewById(R.id.trafficName);
				TextView trafficID = (TextView) temView2
						.findViewById(R.id.trafficID);

				// get detail from server
				String url = serviceIp + Properties.TRAFFIC_TRAFFIC_VIEW
						+ "?id=" + trafficID.getText();
				// System.out.println("url ne : " + url);
				HttpAsyncUtil httpUtil = new HttpAsyncUtil();
				httpUtil.setHttpListener(new IAsyncHttpListener() {
					@Override
					public void onComplete(String response) {
						// TODO Auto-generated method stub

						TrafficInfoJSON trafficInfoJSON = new TrafficInfoJSON();
						Gson gson = new Gson();
						trafficInfoJSON = gson.fromJson(response,
								TrafficInfoJSON.class);
						try {
							// move next screen
							Intent nextScreen = new Intent(
									getApplicationContext(),
									TracfficSignDetailActivity.class);
							try {
								byte[] dataBytes = CommonUtil
										.object2Bytes(trafficInfoJSON);
								nextScreen
										.putExtra("trafficDetails", dataBytes);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							startActivity(nextScreen);

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}// end onComplete
				});
				httpUtil.setUrl(url);
				httpUtil.execute();
			}
		});

	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// display image
				for (int i = 0; i < listFavorite.size(); i++) {
					try {
						int wantedPosition = i;
						if (listFavorite.get(wantedPosition).getTrafficID() != null
								&& listFavorite.get(wantedPosition).getTrafficID()
										.length() > 0) {
							HttpImageUtils httpImgUtil = new HttpImageUtils();
							httpImgUtil.setUrl(serviceIp
									+ listFavorite.get(wantedPosition).getImage() + "?size=small");
							httpImgUtil.setExtra(wantedPosition);
							httpImgUtil
									.setHttpImageListener(new IAsyncHttpImageListener() {

										@Override
										public void onComplete(Bitmap bitmap, int extra) {									
											try{
											View tmpView = lv.getChildAt(extra);
											ImageView icon = (ImageView) tmpView
													.findViewById(R.id.trafficImage);
											icon.setImageBitmap(bitmap);
											}catch(Exception e)
											{
												e.printStackTrace();
											}
										}
									});
							httpImgUtil.execute();
						}// end if length > 0
					} catch (Exception e) {
						e.printStackTrace();
					}
				}// end for
	}

}
