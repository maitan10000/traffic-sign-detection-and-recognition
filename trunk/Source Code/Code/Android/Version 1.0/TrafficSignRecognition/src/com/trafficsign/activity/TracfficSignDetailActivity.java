package com.trafficsign.activity;

import java.io.IOException;

import com.trafficsign.ultils.Properties;
import com.trafficsign.activity.R;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.ultils.CommonUtil;
import com.trafficsign.ultils.HttpImageUtils;
import com.trafficsign.ultils.ImageUtils;
import com.trafficsign.ultils.MyInterface.IAsyncHttpImageListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TracfficSignDetailActivity extends Activity {

	TrafficInfoJSON trafficInfo = null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_traffic_sign_detail);
		Intent intent = getIntent();
		
		// lay du lieu		
		try {
			trafficInfo = (TrafficInfoJSON) CommonUtil.bytes2Object(intent
					.getByteArrayExtra("trafficDetails"));
			this.setTitle(trafficInfo.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Set traffic sign detail to view
		if (trafficInfo != null) {
			//ImageView image = (ImageView) findViewById(R.id.trafficImage);
			TextView name = (TextView) findViewById(R.id.trafficName);
			TextView info = (TextView) findViewById(R.id.trafficContent);
			TextView penaltyFee = (TextView) findViewById(R.id.trafficPenaltyFee);
			TextView id = (TextView) findViewById(R.id.trafficID);
			// //
			name.setText(trafficInfo.getName());
			info.setText(trafficInfo.getInformation());
			penaltyFee.setText(trafficInfo.getPenaltyfee());
			id.setText(trafficInfo.getTrafficID());
			//
			Bitmap bitmap = ImageUtils.convertToBimap(trafficInfo.getImage());
			ImageView image = (ImageView) findViewById(R.id.imageResult);
			image.setImageBitmap(bitmap);
		
		} else {
			// handle null;
			Toast.makeText(getApplicationContext(),
					"Không có thông tin", Toast.LENGTH_LONG);
		}
		
	}
//	@Override
//	protected void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		HttpImageUtils httpImgUtil = new HttpImageUtils();
//		String imageLink = Properties.serviceIp + trafficInfo.getImage();
//		httpImgUtil.setUrl(imageLink);
//		httpImgUtil
//				.setHttpImageListener(new IAsyncHttpImageListener() {
//
//					@Override
//					public void onComplete(Bitmap bitmap,
//							int extra) {
//						ImageView image = (ImageView) findViewById(R.id.imageResult);
//						image.setImageBitmap(bitmap);
//					}
//				});
//		httpImgUtil.execute();
//	}
}
