package com.trafficsign.activity;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.trafficsign.ultils.GlobalValue;
import com.trafficsign.ultils.HttpAsyncUtil;
import com.trafficsign.ultils.MyInterface.IAsyncHttpListener;
import com.trafficsign.ultils.Properties;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReportActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		// TODO Auto-generated method stub
		// get user
		final SharedPreferences pref = getSharedPreferences(
				Properties.SHARE_PREFERENCE_LOGIN, Context.MODE_PRIVATE);
		final String user = pref.getString(
				Properties.SHARE_PREFERENCE__KEY_USER, "");
		Intent intent = getIntent();
		final String feedbackType = intent.getStringExtra("feedbackType");
		final String referenceID = intent.getStringExtra("referenceID");

		// set event onclick for btnFeedback
		Button btnFeedback = (Button) findViewById(R.id.btnFeedback);
		btnFeedback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// get feedback content
				EditText txtFeedbackContent = (EditText) findViewById(R.id.txtFeedbackContent);
				final String feedbackContent = txtFeedbackContent.getText()
						.toString();
				if (feedbackContent.length() > 35 && feedbackContent.length() < 4000) {
					HttpAsyncUtil httpAsyncUtil = new HttpAsyncUtil(
							ReportActivity.this);
					// set parameter
					ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
					parameters.add(new BasicNameValuePair("content",
							feedbackContent));
					parameters.add(new BasicNameValuePair("referenceID",
							referenceID));
					parameters
							.add(new BasicNameValuePair("type", feedbackType));
					parameters.add(new BasicNameValuePair("creator", user));
					httpAsyncUtil.setParameters(parameters);
					httpAsyncUtil.setMethod("POST");
					String urlFeedback = GlobalValue.getServiceAddress()
							+ Properties.MANAGE_REPORT_SEND;
					httpAsyncUtil.setUrl(urlFeedback);
					httpAsyncUtil.setHttpListener(new IAsyncHttpListener() {

						@Override
						public void onComplete(String respond) {
							// TODO Auto-generated method stub
							if ("Success".equals(respond)) {
								Toast.makeText(getApplicationContext(),
										"Cảm ơn bạn đã gửi phản hồi",
										Toast.LENGTH_LONG).show();
								finish();

							} else {
								Toast.makeText(getApplicationContext(),
										"Có lỗi xảy ra, vui lòng thử lại",
										Toast.LENGTH_LONG).show();
							}

						}
					});
					httpAsyncUtil.execute();
				} else {
					Toast.makeText(getApplicationContext(),
							"Nội dung ý kiến quá ngắn hoặc quá dài", Toast.LENGTH_LONG)
							.show();
				}

			}
		});

	}

}
