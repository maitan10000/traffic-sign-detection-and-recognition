package com.example.trafficsignrecognition;

import java.io.IOException;
import java.util.ArrayList;

import com.example.ultils.ConvertUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TracfficSignDetails extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_traffic_sign_detail);
		Intent intent = getIntent();
		this.setTitle(intent.getStringExtra("trafficName"));
		// lay du lieu
		System.out.println("vao dc view details");
		ArrayList<vo.TrafficSign> arr = null;
		try {
			arr = (ArrayList<vo.TrafficSign>) ConvertUtil.bytes2Object(intent
					.getByteArrayExtra("trafficDetails"));
			System.out.println("Size ben nhan: " + arr.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		////////////////////////
		vo.TrafficSign trafficInfo = arr.get(0);
		ImageView image = (ImageView) findViewById(R.id.trafficImage);
		TextView name = (TextView) findViewById(R.id.trafficName);
		TextView info = (TextView) findViewById(R.id.trafficContent);
		TextView penaltyFee = (TextView) findViewById(R.id.trafficPenaltyFee);
		TextView id = (TextView) findViewById(R.id.trafficID);
		////
		name.setText(trafficInfo.getTrafficName());
		info.setText(trafficInfo.getTrafficInfo());
		penaltyFee.setText(trafficInfo.getTrafficPenaltyFee());
		id.setText(trafficInfo.getTrafficID());
		// iamge
//		View view = new View(getApplicationContext());
//		String uri_icon = "drawable/" + trafficInfo.getTrafficImage();
//		int ImageResource = view.getContext().getResources().getIdentifier(uri_icon, null, view.getContext().getApplicationContext().getPackageName());
//		Drawable img = view.getContext().getResources().getDrawable(ImageResource);
//		image.setImageDrawable(img);
	}
}
