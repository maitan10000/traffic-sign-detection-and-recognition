package com.trafficsign.activity;

import java.io.File;
import java.util.ArrayList;

import com.trafficsign.activity.R;
import com.trafficsign.json.ResultInput;
import com.trafficsign.json.TrafficInfoShortJSON;
import com.trafficsign.ultils.GlobalValue;
import com.trafficsign.ultils.HttpImageUtils;
import com.trafficsign.ultils.ImageUtils;
import com.trafficsign.ultils.MyInterface.IAsyncHttpImageListener;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListTrafficArrayAdapter extends ArrayAdapter<TrafficInfoShortJSON> {
	Activity context = null;
	int layoutID;
	ArrayList<TrafficInfoShortJSON> listTrafficInfo = null;

	public ListTrafficArrayAdapter(Activity context, int layoutID,
			ArrayList<TrafficInfoShortJSON> list) {
		super(context, layoutID, list);
		this.context = context;
		this.layoutID = layoutID;
		this.listTrafficInfo = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(layoutID, null);
		}
		TrafficInfoShortJSON traffic = listTrafficInfo.get(position);
		ImageView icon = (ImageView) convertView
				.findViewById(R.id.trafficImage);
		TextView name = (TextView) convertView.findViewById(R.id.trafficName);
		TextView id = (TextView) convertView.findViewById(R.id.trafficID);

		name.setText(traffic.getName());
		id.setText(traffic.getTrafficID());

		Bitmap image = ImageUtils.convertToBimap(traffic.getImage());
		icon.setImageBitmap(image);
		return convertView;

	}

}
