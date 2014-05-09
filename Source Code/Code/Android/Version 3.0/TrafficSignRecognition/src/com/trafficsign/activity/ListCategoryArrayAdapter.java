package com.trafficsign.activity;

import java.util.ArrayList;

import com.trafficsign.activity.R;
import com.trafficsign.json.CategoryJSON;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListCategoryArrayAdapter extends ArrayAdapter<CategoryJSON> {
	Activity context = null;
	int layoutID;
	ArrayList<CategoryJSON> listCategory = null;

	public ListCategoryArrayAdapter(Activity context, int layoutID,
			ArrayList<CategoryJSON> list) {
		super(context, layoutID, list);
		this.context = context;
		this.layoutID = layoutID;
		this.listCategory = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(layoutID, null);
		}
		CategoryJSON cat = listCategory.get(position);
		ImageView icon = (ImageView) convertView
				.findViewById(R.id.trafficImage);
		TextView name = (TextView) convertView.findViewById(R.id.trafficName);
		TextView id = (TextView) convertView.findViewById(R.id.trafficID);

		name.setText(cat.getCategoryName());
		id.setText(cat.getCategoryID());
		if("1".equals(cat.getCategoryID())){
			icon.setImageResource(R.drawable.cam_cat);
		} else if("2".equals(cat.getCategoryID())){
			icon.setImageResource(R.drawable.nguyhiem_cat);
		}else if("3".equals(cat.getCategoryID())){
			icon.setImageResource(R.drawable.hieulenh_cat);
		}else if("4".equals(cat.getCategoryID())){
			icon.setImageResource(R.drawable.huongdan_cat);
		}else if("5".equals(cat.getCategoryID())){
			icon.setImageResource(R.drawable.bienphu_cat);
		}
		return convertView;

	}

}
