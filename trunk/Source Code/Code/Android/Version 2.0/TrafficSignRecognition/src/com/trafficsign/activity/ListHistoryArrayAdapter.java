package com.trafficsign.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.trafficsign.json.ResultShortJSON;
import com.trafficsign.json.TrafficInfoShortJSON;
import com.trafficsign.ultils.ImageUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListHistoryArrayAdapter extends ArrayAdapter<ResultShortJSON> {
	Activity context = null;
	int layoutID;
	ArrayList<ResultShortJSON> listResultShortJSONs = null;

	public ListHistoryArrayAdapter(Activity context, int layoutID,
			ArrayList<ResultShortJSON> list) {
		super(context, layoutID, list);
		this.context = context;
		this.layoutID = layoutID;
		this.listResultShortJSONs = list;

		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(layoutID, null);
		}
		ResultShortJSON resultShort = listResultShortJSONs.get(position);
		TextView resultDate = (TextView) convertView.findViewById(R.id.resultDate);
		TextView resultID = (TextView) convertView.findViewById(R.id.resultID);
		TextView rowPosition = (TextView) convertView.findViewById(R.id.txtPosition);
		
		DateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		resultDate.setText(dateFormat.format(resultShort.getCreateDate()));
		resultID.setText(String.valueOf(resultShort.getResultID()));
		rowPosition.setText(String.valueOf(position + 1));
		return convertView;

	}

}
