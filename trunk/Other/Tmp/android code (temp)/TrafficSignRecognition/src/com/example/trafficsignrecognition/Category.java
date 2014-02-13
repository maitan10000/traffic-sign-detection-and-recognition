package com.example.trafficsignrecognition;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

public class Category extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		final ArrayList<vo.Category> arr = new ArrayList<vo.Category>();
		vo.Category temp = new vo.Category();
		temp.setId("NH001");
		temp.setCatName("Biển báo nguy hiểm");
		temp.setCatImage("trafficicon");
		vo.Category temp2 = new vo.Category();
		temp2.setId("NH001");
		temp2.setCatName("Biển báo cấm");
		temp2.setCatImage("trafficicon");
		vo.Category temp3 = new vo.Category();
		temp3.setId("NH001");
		temp3.setCatName("Biển báo chỉ dẫn");
		temp3.setCatImage("trafficicon");
		arr.add(temp);
		arr.add(temp2);
		arr.add(temp3);
		final ListView lv = (ListView) findViewById(R.id.listCategory);
		MyArrayAdapter myArray = new MyArrayAdapter(this, R.layout.list_rowcat,
				arr);
		lv.setAdapter(myArray);
		//
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				View tempView = lv.getChildAt(position);
				TextView textview = (TextView) tempView
						.findViewById(R.id.trafficName);
				Intent nextScreen = new Intent(getApplicationContext(),
						ListTrafficSign.class);
				nextScreen.putExtra("catName", textview.getText());
				startActivity(nextScreen);
			}
		});
		// TODO Auto-generated method stub
	}

}
