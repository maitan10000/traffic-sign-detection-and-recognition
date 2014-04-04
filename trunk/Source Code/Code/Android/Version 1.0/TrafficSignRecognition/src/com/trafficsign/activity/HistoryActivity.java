package com.trafficsign.activity;

import java.io.IOException;
import java.util.ArrayList;

import com.trafficsign.activity.R;
import com.trafficsign.json.ResultJSON;
import com.trafficsign.json.ResultShortJSON;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryActivity extends Activity {

	ListHistoryArrayAdapter listHistoryArrayAdapter;
	ArrayList<ResultShortJSON> listResultShortJSONs = null;
	static ListView lv = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		listResultShortJSONs = DBUtil.listResult();
		// TODO Auto-generated method stub
		lv = (ListView) findViewById(R.id.listHistory);
		listHistoryArrayAdapter = new ListHistoryArrayAdapter(this,
				R.layout.list_row_history, listResultShortJSONs);
		lv.setAdapter(listHistoryArrayAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				View temView2 = listHistoryArrayAdapter.getView(position, null,
						lv);
				TextView txtResultID = (TextView) temView2
						.findViewById(R.id.resultID);
				String resultID = txtResultID.getText().toString();
				ResultJSON resultJSON = new ResultJSON();
				resultJSON = DBUtil.viewHistory(Integer.parseInt(resultID));
				Intent nextScreen = new Intent(getApplicationContext(),
						ListResultActivity.class);
				nextScreen.putExtra("imagePath", resultJSON.getUploadedImage());
				/* put resultInput to the next screen */
				byte[] dataBytes;
				try {
					dataBytes = ConvertUtil.object2Bytes(resultJSON);
					nextScreen.putExtra("resultJson", dataBytes);
					startActivity(nextScreen);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
		});

	}

}
