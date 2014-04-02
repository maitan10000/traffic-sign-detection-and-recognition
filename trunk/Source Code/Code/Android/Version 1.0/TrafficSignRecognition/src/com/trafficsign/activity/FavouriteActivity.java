package com.trafficsign.activity;

import static com.trafficsign.ultils.Properties.serviceIp;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.trafficsign.activity.R;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.json.TrafficInfoShortJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;
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

	ListTrafficArrayAdapter arrayAdapter;
	ArrayList<TrafficInfoShortJSON> listFavorite = null;
	static ListView lv = null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourite);
		Intent intent = getIntent();
		this.setTitle("Danh sách đã lưu");

		listFavorite = DBUtil.listFavorite();
		/*********************/
		lv = (ListView) findViewById(R.id.listFavorite);
		arrayAdapter = new ListTrafficArrayAdapter(this, R.layout.list_row,
				listFavorite);
		lv.setAdapter(arrayAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				View temView2 = arrayAdapter.getView(position, null, lv);
				TextView trafficID = (TextView) temView2
						.findViewById(R.id.trafficID);
				TrafficInfoJSON trafficInfoJSON = new TrafficInfoJSON();
				trafficInfoJSON = DBUtil.getTrafficDetail(trafficID.getText()
						.toString());
				byte[] dataBytes;
				try {
					// move next screen
					Intent nextScreen = new Intent(getApplicationContext(),
							TracfficSignDetailActivity.class);
					dataBytes = ConvertUtil.object2Bytes(trafficInfoJSON);
					nextScreen.putExtra("trafficDetails", dataBytes);
					startActivity(nextScreen);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});

	}
//	@Override
//	protected void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		// display image
//				for (int i = 0; i < listFavorite.size(); i++) {
//					try {
//						int wantedPosition = i;
//						if (listFavorite.get(wantedPosition).getTrafficID() != null
//								&& listFavorite.get(wantedPosition).getTrafficID()
//										.length() > 0) {
//							HttpImageUtils httpImgUtil = new HttpImageUtils();
//							httpImgUtil.setUrl(serviceIp
//									+ listFavorite.get(wantedPosition).getImage() + "?size=small");
//							httpImgUtil.setExtra(wantedPosition);
//							httpImgUtil
//									.setHttpImageListener(new IAsyncHttpImageListener() {
//
//										@Override
//										public void onComplete(Bitmap bitmap, int extra) {									
//											try{
//											View tmpView = lv.getChildAt(extra);
//											ImageView icon = (ImageView) tmpView
//													.findViewById(R.id.trafficImage);
//											icon.setImageBitmap(bitmap);
//											}catch(Exception e)
//											{
//												e.printStackTrace();
//											}
//										}
//									});
//							httpImgUtil.execute();
//						}// end if length > 0
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}// end for
//	}

}
