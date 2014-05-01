package com.trafficsign.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;


import com.trafficsign.activity.R;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.json.TrafficInfoShortJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.HttpImageUtils;
import com.trafficsign.ultils.HttpAsyncUtil;
import com.trafficsign.ultils.MyInterface.IAsyncHttpImageListener;
import com.trafficsign.ultils.MyInterface.IAsyncHttpListener;
import com.trafficsign.ultils.Properties;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;
import android.view.View;

public class ListTrafficSignActivity extends Activity {

	ListTrafficArrayAdapter arrayAdapter;
	ArrayList<TrafficInfoShortJSON> listTrafficInfo = null;
	static ListView lv = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub
		Intent intent = getIntent();
		this.setTitle(intent.getStringExtra("catName"));
		setContentView(R.layout.activity_listtraffic);

		try {
			listTrafficInfo = (ArrayList<TrafficInfoShortJSON>) ConvertUtil
					.bytes2Object(intent.getByteArrayExtra("trafficList"));
			if (listTrafficInfo == null) {
				listTrafficInfo = new ArrayList<TrafficInfoShortJSON>();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*********************/
		lv = (ListView) findViewById(R.id.listTraffic);
		arrayAdapter = new ListTrafficArrayAdapter(this, R.layout.list_row,
				listTrafficInfo);
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

	// @Override
	// protected void onStart() {
	// // TODO Auto-generated method stub
	// super.onStart();
	// // display image
	// for (int i = 0; i < listTrafficInfo.size(); i++) {
	// try {
	// int wantedPosition = i;
	// if (listTrafficInfo.get(wantedPosition).getTrafficID() != null
	// && listTrafficInfo.get(wantedPosition).getTrafficID()
	// .length() > 0) {
	// HttpImageUtils httpImgUtil = new HttpImageUtils();
	// httpImgUtil.setUrl(serviceIp
	// + listTrafficInfo.get(wantedPosition).getImage()
	// + "?size=small");
	// httpImgUtil.setExtra(wantedPosition);
	// httpImgUtil
	// .setHttpImageListener(new IAsyncHttpImageListener() {
	//
	// @Override
	// public void onComplete(Bitmap bitmap, int extra) {
	// try {
	// View tmpView = arrayAdapter.getView(extra, null, lv);
	// lv.getChildAt(extra);
	// TextView trafficID = (TextView) tmpView
	// .findViewById(R.id.trafficID);
	// trafficID.setText("demo 123");
	//
	// View tmpView1 = arrayAdapter.getView(extra, null, lv);
	// TextView trafficID2 = (TextView) tmpView1
	// .findViewById(R.id.trafficID);
	// Log.e("test 123",trafficID2.getText().toString());
	//
	//
	// //View tmpView = lv.getChildAt(extra);
	// ImageView icon = (ImageView) tmpView
	// .findViewById(R.id.trafficImage);
	// icon.setImageBitmap(bitmap);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// httpImgUtil.execute();
	// }// end if length > 0
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }// end for
	// }

}
