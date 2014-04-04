package com.trafficsign.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.trafficsign.activity.R;
import com.trafficsign.json.ResultJSON;
import com.trafficsign.json.ResultShortJSON;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.GlobalValue;
import com.trafficsign.ultils.HttpUtil;
import com.trafficsign.ultils.NetUtil;
import com.trafficsign.ultils.Properties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
		Collections.sort(listResultShortJSONs);
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

		registerForContextMenu(lv);

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		System.out.println("...on create context menu...");
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.listHistory) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle("Title");
			menu.add(Menu.NONE, 0, 0, "Xóa");

		}
	}

	int resultID;

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		if (menuItemIndex == 0) {
			// delete
			resultID = listResultShortJSONs.get(info.position).getResultID();
			listResultShortJSONs.remove(info.position);
			listHistoryArrayAdapter.notifyDataSetChanged();
			Thread thread = new Thread(new Runnable() {
				public void run() {					
					String urlDeleteHistory = GlobalValue.getServiceAddress()
							+ Properties.TRAFFIC_HISTORY_DELETE + "?id="
							+ resultID;
					if (NetUtil.isAccessService() == true) {
						String response = HttpUtil.get(urlDeleteHistory);
						if("Success".equals(response)){
							DBUtil.deleteResult(resultID);
						}
					}else{
						DBUtil.deActivateResult(resultID);
					}
				}
			});
			thread.start();
		}
		return true;
	}
}
