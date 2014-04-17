package com.trafficsign.activity;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.trafficsign.ultils.Properties;
import com.trafficsign.activity.R;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.json.TrafficInfoShortJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.GlobalValue;
import com.trafficsign.ultils.HttpAsyncUtil;
import com.trafficsign.ultils.HttpImageUtils;
import com.trafficsign.ultils.ImageUtils;
import com.trafficsign.ultils.MyInterface.IAsyncHttpImageListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TracfficSignDetailActivity extends Activity {

	TrafficInfoJSON trafficInfo = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_traffic_sign_detail);
		Intent intent = getIntent();

		// lay du lieu
		try {
			trafficInfo = (TrafficInfoJSON) ConvertUtil.bytes2Object(intent
					.getByteArrayExtra("trafficDetails"));
			this.setTitle(trafficInfo.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Set traffic sign detail to view
		if (trafficInfo != null) {
			final ImageButton btnFavorite = (ImageButton) findViewById(R.id.btnFavourite);
			final ImageButton btnFeedback = (ImageButton) findViewById(R.id.btnSendFeedback);
			// get user
			SharedPreferences pref = getSharedPreferences(
					Properties.SHARE_PREFERENCE_LOGIN, MODE_PRIVATE);
			final String user = pref.getString(
					Properties.SHARE_PREFERENCE__KEY_USER, "");
			// check favorite status to display button image
			if (DBUtil.checkFavoriteStatus(trafficInfo.getTrafficID()) == DBUtil.DEACTIVE || DBUtil.checkFavoriteStatus(trafficInfo.getTrafficID()) == DBUtil.NOT_EXIST) {

				btnFavorite.setBackgroundResource(R.drawable.btn_star_big_off);
			} else if (DBUtil.checkFavoriteStatus(trafficInfo.getTrafficID()) == DBUtil.ACTIVE) {
				btnFavorite.setBackgroundResource(R.drawable.btn_star_big_on);
			}
			btnFavorite.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// if traffic is not add or delete before excute add
					// favorite
					if (DBUtil.checkFavoriteStatus(trafficInfo.getTrafficID()) == DBUtil.NOT_EXIST) {
						TrafficInfoShortJSON infoShortJSON = new TrafficInfoShortJSON();
						infoShortJSON.setImage(trafficInfo.getImage());
						infoShortJSON.setName(trafficInfo.getName());
						infoShortJSON.setTrafficID(trafficInfo.getTrafficID());
						// set current dat
						DateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
						Calendar cal = Calendar.getInstance();
						infoShortJSON.setModifyDate(new Date(cal.getTime()
								.getTime()));
						// add favorite to db
						DBUtil.addFavorite(infoShortJSON, user);
						// add favorite to service if can access to server
						ArrayList<NameValuePair> parameter = new ArrayList<NameValuePair>();
						parameter.add(new BasicNameValuePair("creator", user));
						parameter.add(new BasicNameValuePair("trafficID",
								trafficInfo.getTrafficID()));
						// change button image
						btnFavorite
								.setBackgroundResource(R.drawable.btn_star_big_on);
						// url for add favorite
						String url = GlobalValue.getServiceAddress()
								+ Properties.MANAGE_FAVORITE_ADD;
						HttpAsyncUtil httpAsyncUtil = new HttpAsyncUtil();
						httpAsyncUtil.setMethod("POST");
						httpAsyncUtil.setParameters(parameter);
						httpAsyncUtil.setUrl(url);
						httpAsyncUtil.execute();

					} else
					// if favorite is active, excute deactive
					if (DBUtil.checkFavoriteStatus(trafficInfo.getTrafficID()) == DBUtil.ACTIVE) {
						// deactive in db
						DBUtil.deActivateFavorite(trafficInfo.getTrafficID());
						// change button image
						btnFavorite
								.setBackgroundResource(R.drawable.btn_star_big_off);
						// url for delete favorite
						String url = GlobalValue.getServiceAddress()
								+ Properties.MANAGE_FAVORITE_DELETE
								+ "?creator=" + user + "&trafficID=";
						url += trafficInfo.getTrafficID();
						HttpAsyncUtil httpAsyncUtil = new HttpAsyncUtil();
						httpAsyncUtil.setUrl(url);
						httpAsyncUtil.execute();
					} else
					// favorite is deActive, excute active
					{
						// active in db
						DBUtil.activateFavorite(trafficInfo.getTrafficID());
						// change button image
						btnFavorite
								.setBackgroundResource(R.drawable.btn_star_big_on);
						// add favorite to service if can access to server
						ArrayList<NameValuePair> parameter = new ArrayList<NameValuePair>();
						parameter.add(new BasicNameValuePair("creator", user));
						parameter.add(new BasicNameValuePair("trafficID",
								trafficInfo.getTrafficID()));
						// change button image
						btnFavorite
								.setBackgroundResource(R.drawable.btn_star_big_on);
						// url for add favorite
						String url = GlobalValue.getServiceAddress()
								+ Properties.MANAGE_FAVORITE_ADD;
						HttpAsyncUtil httpAsyncUtil = new HttpAsyncUtil();
						httpAsyncUtil.setMethod("POST");
						httpAsyncUtil.setParameters(parameter);
						httpAsyncUtil.setUrl(url);
						httpAsyncUtil.execute();
					}
				}
			});
			btnFeedback.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent nextScreen = new Intent(getApplicationContext(), ReportActivity.class);
					nextScreen.putExtra("feedbackType", "2");
					nextScreen.putExtra("referenceID", trafficInfo.getTrafficID());
					startActivity(nextScreen);
					
				}
			});

			// ImageView image = (ImageView) findViewById(R.id.trafficImage);
			TextView name = (TextView) findViewById(R.id.trafficName);
			TextView info = (TextView) findViewById(R.id.trafficContent);
			TextView penaltyFee = (TextView) findViewById(R.id.trafficPenaltyFee);
			TextView id = (TextView) findViewById(R.id.trafficID);
			// //
			name.setText(trafficInfo.getName());
			info.setText(trafficInfo.getInformation());
			penaltyFee.setText(trafficInfo.getPenaltyfee());
			id.setText(trafficInfo.getTrafficID());
			//
			Bitmap bitmap = ImageUtils.convertToBimap(trafficInfo.getImage());
			ImageView image = (ImageView) findViewById(R.id.imageResult);
			image.setImageBitmap(bitmap);

		} else {
			// handle null;
			Toast.makeText(getApplicationContext(), "Không có thông tin",
					Toast.LENGTH_LONG);
		}

	}

}
