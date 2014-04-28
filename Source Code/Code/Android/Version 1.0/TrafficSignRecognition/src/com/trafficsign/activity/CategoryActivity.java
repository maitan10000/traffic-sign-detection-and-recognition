package com.trafficsign.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trafficsign.activity.R;
import com.trafficsign.json.CategoryJSON;
import com.trafficsign.json.TrafficInfoShortJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.HttpAsyncUtil;
import com.trafficsign.ultils.MyInterface.IAsyncHttpListener;
import com.trafficsign.ultils.Properties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import static com.trafficsign.ultils.Properties.serviceIpOnline;

public class CategoryActivity extends Activity {

	ArrayList<CategoryJSON> listCategory = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		Intent intent = getIntent();
		try {
			listCategory = (ArrayList<CategoryJSON>) ConvertUtil
					.bytes2Object(intent.getByteArrayExtra("catList"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final ListView lv = (ListView) findViewById(R.id.listFavorite);
		ListCategoryArrayAdapter myArray = new ListCategoryArrayAdapter(this,
				R.layout.list_rowcat, listCategory);
		lv.setAdapter(myArray);
		//
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				CategoryJSON categoryJSON = listCategory.get(position);
				ArrayList<TrafficInfoShortJSON> listTrafficInfo = new ArrayList<TrafficInfoShortJSON>();
				listTrafficInfo = DBUtil.getTrafficByCategory(categoryJSON.getCategoryID());
				byte[] dataBytes;
				try {
					Intent nextScreen = new Intent(
							getApplicationContext(),
							ListTrafficSignActivity.class);
					dataBytes = ConvertUtil
							.object2Bytes(listTrafficInfo);
					nextScreen.putExtra("trafficList", dataBytes);
					nextScreen
							.putExtra("catName", "Danh Sách Biển Báo");
					startActivity(nextScreen);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}
		});
		//
		ImageButton btnSearch = (ImageButton) findViewById(R.id.searchButton);
		btnSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View V) {
				// TODO Auto-generated method stub
				// Get search key from edittext in layout
				EditText searchKey = (EditText) findViewById(R.id.searchTraffic);
				String keyWord = searchKey.getText().toString();

				ArrayList<TrafficInfoShortJSON> listTrafficInfo = new ArrayList<TrafficInfoShortJSON>();
				listTrafficInfo = DBUtil.getTrafficByName(keyWord);
				byte[] dataBytes;
				try {
					Intent nextScreen = new Intent(
							getApplicationContext(),
							ListTrafficSignActivity.class);
					dataBytes = ConvertUtil
							.object2Bytes(listTrafficInfo);
					nextScreen.putExtra("trafficList", dataBytes);
					nextScreen
							.putExtra("catName", "Kết quả tìm kiếm");
					startActivity(nextScreen);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// TODO Auto-generated method stub
	}
}
