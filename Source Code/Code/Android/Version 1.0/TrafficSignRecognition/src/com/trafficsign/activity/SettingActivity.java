package com.trafficsign.activity;

import com.trafficsign.ultils.Properties;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class SettingActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_setting);
	    // TODO Auto-generated method stub
	    Switch switchWifi = (Switch) findViewById(R.id.switchWifi);
	    Switch switchNotification = (Switch) findViewById(R.id.switchNotification);
	    final SharedPreferences pref  = getSharedPreferences(Properties.SHARE_PREFERENCE_SETTING, MODE_PRIVATE);
	    boolean wifiSetting = pref.getBoolean(Properties.SHARE_PREFERENCE_KEY_WIFI, true);
	    boolean notiSetting = pref.getBoolean(Properties.SHARE_PREFERENCE__KEY_NOTI, true);
	    // set state for switch when start screen setting
	    switchWifi.setChecked(wifiSetting);
	    switchNotification.setChecked(notiSetting);
	    // set event onchecked change for wifi switch
	    switchWifi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Editor edittor = pref.edit();
				edittor.putBoolean(Properties.SHARE_PREFERENCE_KEY_WIFI, isChecked);
				edittor.commit();
			}
		});
	    switchNotification.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Editor edittor = pref.edit();
				edittor.putBoolean(Properties.SHARE_PREFERENCE__KEY_NOTI, isChecked);
				edittor.commit();
			}
		});
	    
	}

}
