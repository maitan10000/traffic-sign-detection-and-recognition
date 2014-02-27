package com.example.trafficsignrecognition;

import java.util.ArrayList;

import vo.ResultInput;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListResultArrayAdapter extends ArrayAdapter<ResultInput> {
	Activity context = null;
	int layoutID;
	ArrayList<ResultInput> arr = null;

	public ListResultArrayAdapter(Activity context, int layoutID,
			ArrayList<ResultInput> list) {
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
		ResultInput resultInput = arr.get(position);
		ImageView icon = (ImageView) convertView.findViewById(R.id.trafficImage);
		TextView name = (TextView) convertView.findViewById(R.id.trafficName);
		TextView id = (TextView) convertView.findViewById(R.id.trafficID);

		name.setText(resultInput.getTrafficName());
		id.setText(resultInput.getTrafficID());
		// image resource
		String uri_icon = "drawable/" + resultInput.getTrafficImage();
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
