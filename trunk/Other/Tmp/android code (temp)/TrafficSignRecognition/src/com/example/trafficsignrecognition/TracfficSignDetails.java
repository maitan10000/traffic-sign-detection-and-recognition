package com.example.trafficsignrecognition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class TracfficSignDetails extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_traffic_sign_detail);
		Intent intent = getIntent();
		this.setTitle(intent.getStringExtra("trafficName"));
	}
}
