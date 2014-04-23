package com.trafficsign.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.trafficsign.ultils.GlobalValue;
import com.trafficsign.ultils.HttpAsyncUtil;
import com.trafficsign.ultils.MyInterface.IAsyncHttpListener;
import com.trafficsign.ultils.Properties;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// TODO Auto-generated method stub
		final EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
		final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// get username and password
				final String username = txtUsername.getText().toString().trim();
				String password = txtPassword.getText().toString().trim();
				// handlde if user dose not enter username or password
				if (username.length() == 0 && password.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Vui lòng nhập tên tài khoản và mật khẩu",
							Toast.LENGTH_SHORT).show();
				} else if (username.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Vui lòng nhập tên tài khoản", Toast.LENGTH_SHORT)
							.show();
					txtUsername.requestFocus();
				} else if (password.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT)
							.show();
					txtPassword.requestFocus();
				} else { // if username and password is entered
					// url login
					String urlLogin = GlobalValue.getServiceAddress()
							+ Properties.MANAGE_LOGIN;
					// prepare parameter for POST request
					List<NameValuePair> parameter = new ArrayList<NameValuePair>();
					parameter.add(new BasicNameValuePair("userID", username));
					parameter.add(new BasicNameValuePair("password", password));
					HttpAsyncUtil httpUtil = new HttpAsyncUtil(
							LoginActivity.this);
					httpUtil.setMethod("POST");
					httpUtil.setParameters(parameter);
					httpUtil.setUrl(urlLogin);
					httpUtil.setHttpListener(new IAsyncHttpListener() {

						@Override
						public void onComplete(String respond) {
							// TODO Auto-generated method stub
							// if username and password is correct
							if (respond.length() > 0
									&& "serverFail".equals(respond) == false) {
								// save userID into sahre preference
								SharedPreferences sharedPreferences = getSharedPreferences(Properties.SHARE_PREFERENCE_LOGIN,MODE_PRIVATE );
								SharedPreferences.Editor editor = sharedPreferences.edit();
								editor.putString(Properties.SHARE_PREFERENCE__KEY_USER, username);
								editor.putBoolean(Properties.SHARE_PREFERENCE__KEY_USER_SYNC, false);
								editor.commit();
								// Move to main activity
								Intent nexscreen = new Intent(getApplicationContext(),MainActivity.class);
								finish();
								startActivity(nexscreen);
							} else
							// if username and password is wrong
							if (respond.length() == 0) {
								Toast.makeText(
										getApplicationContext(),
										"Tên tài khoản hoặc mật khẩu không đúng. Vui lòng thử lại",
										Toast.LENGTH_SHORT).show();

							} else
							// if cannot access to server
							if ("serverFail".equals(respond) == true) {
								Toast.makeText(
										getApplicationContext(),
										"Không thể kết nối tới máy chủ. Vui lòng kiểm tra kết nối và thử lại!!!",
										Toast.LENGTH_SHORT).show();
							}
						}
					});
					httpUtil.execute();

				}

			}
		});
	}

}
