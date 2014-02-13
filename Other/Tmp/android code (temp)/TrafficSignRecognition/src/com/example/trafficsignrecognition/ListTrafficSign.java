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

public class ListTrafficSign extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    Intent intent = getIntent();
	    this.setTitle(intent.getStringExtra("catName"));
	    setContentView(R.layout.activity_listtraffic);
		final ArrayList<vo.TrafficSign> arr = new ArrayList<vo.TrafficSign>();
		vo.TrafficSign temp = new vo.TrafficSign();
		temp.setTrafficID("NH001");
		temp.setTrafficName("Cấm đi ngược chiều");
		temp.setTrafficImage("cam_di_nguoc_chieu");
		//
		vo.TrafficSign temp2 = new vo.TrafficSign();
		temp2.setTrafficID("NH002");
		temp2.setTrafficName("Cấm rẽ");
		temp2.setTrafficImage("cam_re");
		for(int i = 0; i <= 10;i++){
			arr.add(temp);
			arr.add(temp2);
		}
		final ListView lv =  (ListView) findViewById(R.id.listTraffic);
		ListTrafficArrayAdapter myArray = new ListTrafficArrayAdapter(this, R.layout.list_row, arr);
		lv.setAdapter(myArray);
		lv.setOnItemClickListener( new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v,int position, long id){
				View tempView = lv.getChildAt(position);
				System.out.println("vi tri ne : " + position);
				TextView trafficName = (TextView)tempView.findViewById(R.id.trafficName);
				TextView trafficID = (TextView)tempView.findViewById(R.id.trafficID);
				Intent nextScreen = new Intent(getApplicationContext(), TracfficSignDetails.class);
				nextScreen.putExtra("trafficName", trafficName.getText());
				nextScreen.putExtra("trafficID",trafficID.getText());
				startActivity(nextScreen);
			}
		});
	}

}
