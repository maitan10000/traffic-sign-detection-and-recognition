package com.trafficsign.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.trafficsign.ultils.GlobalValue;
import com.trafficsign.ultils.Helper;
import com.trafficsign.ultils.HttpAsyncUtil;
import com.trafficsign.ultils.MyInterface.IAsyncHttpListener;
import com.trafficsign.ultils.Properties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	String userName;
	String userID;
	String password;
	String rePassword;
	String emailString;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		// TODO Auto-generated method stub
		Button btnRegister = (Button) findViewById(R.id.btnRegister);
		final EditText txtUsername = (EditText) findViewById(R.id.txtUserFullName);
		final EditText txtUserID = (EditText) findViewById(R.id.txtUserID);
		final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
		final EditText txtRePassword = (EditText) findViewById(R.id.txtReTypePassword);
		final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);

		// set event for button register
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userName = txtUsername.getText().toString();
				userID = txtUserID.getText().toString();
				password = txtPassword.getText().toString();
				rePassword = txtRePassword.getText().toString();
				emailString = txtEmail.getText().toString();
				// validate input
				if (userName.trim().length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Vui lòng nhập họ và tên", Toast.LENGTH_SHORT)
							.show();
					txtUsername.requestFocus();
				} else if (userID.trim().length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Vui lòng nhập tên tài khoản", Toast.LENGTH_SHORT)
							.show();
					txtUserID.requestFocus();
				} else if (password.trim().length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT)
							.show();
					txtPassword.requestFocus();
				} else if (rePassword.trim().length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Vui lòng nhập xác nhận mật khẩu",
							Toast.LENGTH_SHORT).show();
					txtRePassword.requestFocus();
				} else if (emailString.trim().length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Vui lòng nhập email", Toast.LENGTH_SHORT).show();
					txtEmail.requestFocus();
				} else if (Helper.isEmail(emailString.trim()) == false) {
					Toast.makeText(getApplicationContext(),
							"Email không đúng, vui long thử lại",
							Toast.LENGTH_SHORT).show();
					txtEmail.requestFocus();
				} else if (password.trim().equals(rePassword.trim()) == false) {
					Toast.makeText(getApplicationContext(),
							"Mật khẩu xác nhận không đúng, vui lòng thử lại",
							Toast.LENGTH_SHORT).show();
				} else { // if input is validate
					String urlRegister = GlobalValue.getServiceAddress()
							+ Properties.MANAGE_REGISTER;
					// prepare parameter for POST request
					List<NameValuePair> parameter = new ArrayList<NameValuePair>();
					parameter.add(new BasicNameValuePair("userID", userID));
					parameter.add(new BasicNameValuePair("password", password));
					parameter.add(new BasicNameValuePair("email", emailString));
					parameter.add(new BasicNameValuePair("name", userName));
					HttpAsyncUtil httpUtil = new HttpAsyncUtil(
							RegisterActivity.this);
					httpUtil.setMethod("POST");
					httpUtil.setParameters(parameter);
					httpUtil.setUrl(urlRegister);
					httpUtil.setHttpListener(new IAsyncHttpListener() {

						@Override
						public void onComplete(String respond) {
							// TODO Auto-generated method stub
							if ("User exist".equals(respond)) {
								Toast.makeText(
										getApplicationContext(),
										"Tên tài khoản đã có, vui lòng thử lại",
										Toast.LENGTH_SHORT).show();
								txtUserID.requestFocus();
							} else if ("Email exist".equals(respond)) {
								Toast.makeText(getApplicationContext(),
										"Email đã có, vui lòng thử lại",
										Toast.LENGTH_SHORT).show();
								txtEmail.requestFocus();
							} else if (userID.equals(respond)) {
								Toast.makeText(
										getApplicationContext(),
										"Đăng kí thành công. Vui lòng xác nhận email để hoàn tất.",
										Toast.LENGTH_SHORT).show();
								Intent nexscreen = new Intent(
										getApplicationContext(),
										LoginActivity.class);
								finish();
								startActivity(nexscreen);
							}
						}
					});
					httpUtil.execute();

				}
			}
		});

	}

}
