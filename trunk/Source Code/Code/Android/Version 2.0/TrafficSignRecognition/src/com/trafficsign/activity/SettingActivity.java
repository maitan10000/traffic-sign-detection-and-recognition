package com.trafficsign.activity;

import com.trafficsign.ultils.GlobalValue;
import com.trafficsign.ultils.Helper;
import com.trafficsign.ultils.Properties;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;

public class SettingActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		// TODO Auto-generated method stub
		// get type of server
		String typeServer = Helper.getProperty(GlobalValue.getAppFolder()
				+ Properties.SETTING_FILE_PATH,
				Properties.PROPERTIES_KEY_SERVER);
		//
		Switch switchWifi = (Switch) findViewById(R.id.switchWifi);
		Switch switchNotification = (Switch) findViewById(R.id.switchNotification);
		final SharedPreferences prefSetting = getSharedPreferences(
				Properties.SHARE_PREFERENCE_SETTING, MODE_PRIVATE);
		boolean wifiSetting = prefSetting.getBoolean(
				Properties.SHARE_PREFERENCE_KEY_WIFI, true);
		boolean notiSetting = prefSetting.getBoolean(
				Properties.SHARE_PREFERENCE__KEY_NOTI, true);
		// set state for switch when start screen setting
		switchWifi.setChecked(wifiSetting);
		switchNotification.setChecked(notiSetting);
		// set event onchecked change for wifi switch
		switchWifi.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				Editor edittor = prefSetting.edit();
				edittor.putBoolean(Properties.SHARE_PREFERENCE_KEY_WIFI,
						isChecked);
				edittor.commit();
			}
		});
		// set event onchecked change for notification switch
		switchNotification
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						Editor edittor = prefSetting.edit();
						edittor.putBoolean(
								Properties.SHARE_PREFERENCE__KEY_NOTI,
								isChecked);
						edittor.commit();
					}
				});
		/*-------------------------*/
		// get search auto time
		final Long autoTime = prefSetting.getLong(
				Properties.SHARE_PREFERENCE__KEY_SEARCH_AUTO_TIME, 3);
		// get spinner
		final Spinner spinnerSearchAuto = (Spinner) findViewById(R.id.spinnerSearchAuto);
		// set selected value for spinner
		String selectedValue1 = autoTime + " giây";
		ArrayAdapter<String> spinnerSearchAutoAdapter = (ArrayAdapter<String>) spinnerSearchAuto
				.getAdapter();
		int itemPosition1 = spinnerSearchAutoAdapter
				.getPosition(selectedValue1);
		spinnerSearchAuto.setSelection(itemPosition1);
		// set event for spinnersearchauto
		spinnerSearchAuto
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						String selectedValue = String.valueOf(spinnerSearchAuto
								.getSelectedItem());
						String[] split = selectedValue.split(" ");
						Long time = Long.parseLong(split[0]);
						if (time != autoTime) {

							Editor edittor = prefSetting.edit();
							edittor.putLong(
									Properties.SHARE_PREFERENCE__KEY_SEARCH_AUTO_TIME,
									time);
							edittor.commit();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		/*-------------------------*/
		// get update time
		final Long saveTime = prefSetting.getLong(
				Properties.SHARE_PREFERENCE__KEY_TRAFFIC_UPDATE_TIME, 30);
		// get spinner
		final Spinner spinner = (Spinner) findViewById(R.id.spinnerUpdate);
		// set selected value for spinner
		String selectedValue = saveTime + " ngày";
		ArrayAdapter<String> spinnerAdapter = (ArrayAdapter<String>) spinner
				.getAdapter();
		int itemPosition = spinnerAdapter.getPosition(selectedValue);
		spinner.setSelection(itemPosition);
		// set item selected event for spinner
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String selectedValue = String.valueOf(spinner.getSelectedItem());
				String[] split = selectedValue.split(" ");
				Long time = Long.parseLong(split[0]);
				if (time != saveTime) {

					Editor edittor = prefSetting.edit();
					edittor.putLong(
							Properties.SHARE_PREFERENCE__KEY_TRAFFIC_UPDATE_TIME,
							time);
					// set flag
					edittor.putString(
							Properties.SHARE_PREFERENCE__KEY_TRAFFIC_UPDATE_ALARM,
							"");
					edittor.commit();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

	}

}
