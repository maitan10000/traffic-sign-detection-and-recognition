package com.example.trafficsignrecognition;

import java.util.ArrayList;

import vo.TrafficSign;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListTrafficArrayAdapter extends ArrayAdapter<TrafficSign> {
	Activity context = null;
	int layoutID;
	ArrayList<TrafficSign> arr = null;

	public ListTrafficArrayAdapter(Activity context, int layoutID,
			ArrayList<TrafficSign> list) {
		super(context, layoutID, list);
		this.context = context;
		this.layoutID = layoutID;
		this.arr = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(layoutID, null);
		}
		TrafficSign traffic = arr.get(position);
		ImageView icon = (ImageView) convertView.findViewById(R.id.trafficImage);
		TextView name = (TextView) convertView.findViewById(R.id.trafficName);
		TextView id = (TextView) convertView.findViewById(R.id.trafficID);

		name.setText(traffic.getTrafficName());
		id.setText(traffic.getTrafficID());
		// image resource
		String uri_icon = "drawable/" + traffic.getTrafficImage();
		int ImageResource = convertView
				.getContext()
				.getResources()
				.getIdentifier(
						uri_icon,
						null,
						convertView.getContext().getApplicationContext()
								.getPackageName());
		Drawable image = convertView.getContext().getResources()
				.getDrawable(ImageResource);
		icon.setImageDrawable(image);
		return convertView;

	}

}
