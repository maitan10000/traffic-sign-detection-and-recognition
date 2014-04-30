package com.trafficsign.activity;

import static com.trafficsign.ultils.Properties.serviceIpOnline;

import java.io.IOException;
import java.util.ArrayList;


import com.trafficsign.activity.R;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.json.TrafficInfoShortJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.GlobalValue;
import com.trafficsign.ultils.HttpImageUtils;
import com.trafficsign.ultils.HttpAsyncUtil;
import com.trafficsign.ultils.HttpUtil;
import com.trafficsign.ultils.NetUtil;
import com.trafficsign.ultils.Properties;
import com.trafficsign.ultils.MyInterface.IAsyncHttpImageListener;
import com.trafficsign.ultils.MyInterface.IAsyncHttpListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FavouriteActivity extends Activity {

	ListTrafficArrayAdapter arrayAdapter;
	ArrayList<TrafficInfoShortJSON> listFavorite = null;
	static ListView lv = null;
	String user = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourite);
		Intent intent = getIntent();
		this.setTitle("Danh sách đã lưu");
		// get user
		SharedPreferences pref = getSharedPreferences(
				Properties.SHARE_PREFERENCE_LOGIN, MODE_PRIVATE);
		user = pref.getString(Properties.SHARE_PREFERENCE__KEY_USER, "");

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
		registerForContextMenu(lv);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		System.out.println("...on create context menu...");
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.listFavorite) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle("Xóa biển báo");
			menu.add(Menu.NONE, 0, 0, "Xóa");

		}
	}

	String trafficID;

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		if (menuItemIndex == 0) {
			// delete
			trafficID = listFavorite.get(info.position).getTrafficID();
			listFavorite.remove(info.position);
			arrayAdapter.notifyDataSetChanged();
					// deactive in db
					DBUtil.deActivateFavorite(trafficID);
					// url for delete favorite
					String url = GlobalValue.getServiceAddress()
							+ Properties.MANAGE_FAVORITE_DELETE + "?creator="
							+ user + "&trafficID=";
					url += trafficID;
					HttpAsyncUtil httpAsyncUtil = new HttpAsyncUtil();
					httpAsyncUtil.setUrl(url);
					httpAsyncUtil.execute();		
		}
		return true;
	}

}
