package com.trafficsign.activity;

import static com.trafficsign.ultils.Properties.isTaken;
import static com.trafficsign.ultils.Properties.serviceIp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.google.gson.Gson;
import com.trafficsign.json.LocateJSON;
import com.trafficsign.json.ResultDB;
import com.trafficsign.json.ResultInput;
import com.trafficsign.json.ResultJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.GlobalValue;
import com.trafficsign.ultils.Helper;
import com.trafficsign.ultils.NetUtil;
import com.trafficsign.ultils.Properties;
import com.trafficsign.ultils.UploadUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageReviewActivity extends Activity {
String imagePath;
private String user = "";
static {
	if (!OpenCVLoader.initDebug())
		Log.d("debug", "Unable to load OpenCV List result");
	else
		Log.d("debug", "OpenCV loaded List result");
}

private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
	@Override
	public void onManagerConnected(int status) {
		switch (status) {
		case LoaderCallbackInterface.SUCCESS: {
			// Log.i(TAG, "OpenCV loaded successfully");
			// mOpenCvCameraView.enableView();
			// mOpenCvCameraView.setOnTouchListener(CameraActivity.this);
		}
			break;
		default: {
			super.onManagerConnected(status);
		}
			break;
		}
	}
};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_image_review);
	    // get user
	    final SharedPreferences pref = getSharedPreferences(
				Properties.SHARE_PREFERENCE_LOGIN, Context.MODE_PRIVATE);
		user = pref.getString("user", "");
	    // TODO Auto-generated method stub
		Intent intent = getIntent();
		 imagePath = intent.getStringExtra("imagePath");
		Bitmap image = BitmapFactory.decodeFile(imagePath);
		// set image to imageview
		ImageView imageView = (ImageView) findViewById(R.id.imageUpload);
		imageView.setImageBitmap(image);
		// upload file ****************************
		final String upLoadServerUri = GlobalValue.getServiceAddress()
				+ Properties.TRAFFIC_SEARCH_AUTO;
		new Thread(new Runnable() {


			public void run() {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
				String currentDateandTime = sdf.format(new Date());
				String fileName = GlobalValue.getAppFolder() + Properties.SAVE_IMAGE_FOLDER
						+ currentDateandTime + ".jpg";
				// coppy image to saveimage folder
				Helper.copyFileUsingStream(imagePath, fileName);
				// get screen size
				Display display = getWindowManager().getDefaultDisplay();
				Point screenSize = new Point();
				display.getSize(screenSize);
				// Resize
				org.opencv.core.Size size = new org.opencv.core.Size();
				size.height = screenSize.y;
				size.width = screenSize.x;

				Mat tmpImage = Highgui.imread(fileName);
				Mat tmpResize = new Mat(size, tmpImage.type());
				Imgproc.resize(tmpImage, tmpResize, tmpResize.size());
				Highgui.imwrite(fileName, tmpResize);
				// end resize

				// create post data
				ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
				parameters.add(new BasicNameValuePair("userID", user));
				// if access to server ok
				if (NetUtil.networkState(ImageReviewActivity.this) > Properties.INTERNET_SETTING) {


					String jsonString = UploadUtils.uploadFile(fileName,
							upLoadServerUri, parameters);

					if (jsonString.contains("null")
							|| jsonString.trim().isEmpty()) {
						Toast.makeText(getApplicationContext(),
								jsonString, Toast.LENGTH_LONG)
								.show();

					} else {
						Log.e("JsonString: " , jsonString);
						Gson gson = new Gson();
						ResultJSON resultJson = new ResultJSON();
						resultJson = gson.fromJson(jsonString,
								ResultJSON.class);
						// ResultInput resultTmp = resultInput.get(0);
						// save result to db in mobile
						ResultDB resultDB = new ResultDB();
						resultDB.setCreateDate(resultJson.getCreateDate());
						resultDB.setCreator(resultJson.getCreator());
						resultDB.setLocate(gson.toJson(resultJson
								.getListTraffic()));
						resultDB.setResultID(resultJson.getResultID());
						resultDB.setUploadedImage(fileName);
						//DBUtil.addResult(resultDB, user);
						// create intent
						Intent nextScreen = new Intent(
								getApplicationContext(),
								ListResultActivity.class);
						nextScreen.putExtra("imagePath", fileName);
						/* put resultInput to the next screen */
						byte[] dataBytes;
						try {
							dataBytes = ConvertUtil
									.object2Bytes(resultJson);
							nextScreen.putExtra("resultJson", dataBytes);
							startActivity(nextScreen);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				} else { // in case can not access to server
					isTaken = false;
					// save searchInfo to DB
					DBUtil.saveSearchInfo(fileName, "");
					Intent nextScreen = new Intent(getApplicationContext(),
							MainActivity.class);
					startActivity(nextScreen);
				}

			}
		}).start();
	}

}
