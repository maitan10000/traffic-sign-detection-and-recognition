package com.example.trafficsignrecognition;

import java.util.ArrayList;

import vo.Category;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<Category> {
	Activity context = null;
	int layoutID;
	ArrayList<Category> arr = null;

	public MyArrayAdapter(Activity context, int layoutID,
			ArrayList<Category> list) {
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
		Category cat = arr.get(position);
		ImageView icon = (ImageView) convertView
				.findViewById(R.id.trafficImage);
		TextView name = (TextView) convertView.findViewById(R.id.trafficName);
		TextView id = (TextView) convertView.findViewById(R.id.trafficID);

		name.setText(cat.getCatName());
		id.setText(cat.getId());
		// image resource
		String uri_icon = "drawable/" + cat.getCatImage();
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
