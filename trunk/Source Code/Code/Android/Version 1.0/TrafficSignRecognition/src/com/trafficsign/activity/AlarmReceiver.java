package com.trafficsign.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
		Log.e("alarm", "alarm trigger");
		 Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_LONG).show();
	}

}
