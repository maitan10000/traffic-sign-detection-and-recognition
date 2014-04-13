package com.trafficsign.activity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Mat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trafficsign.activity.R;
import com.trafficsign.json.ResultInput;
import com.trafficsign.json.ResultJSON;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.HttpImageUtils;
import com.trafficsign.ultils.HttpAsyncUtil;
import com.trafficsign.ultils.ImageUtils;
import com.trafficsign.ultils.MyInterface.IAsyncHttpImageListener;
import com.trafficsign.ultils.MyInterface.IAsyncHttpListener;
import com.trafficsign.ultils.Properties;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import static com.trafficsign.ultils.Properties.serviceIp;

public class ListResultActivity extends Activity {

	ListResultArrayAdapter listResultAdapter;
	static ResultJSON resultJson;
	static ArrayList<ResultInput> listResult;
	static ListView lv;

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
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_result);
		this.setTitle("Kết quả nhận dạng");
		Intent intent = getIntent();
		String imagePath = intent.getStringExtra("imagePath");

		// Get list from extra
		try {
			resultJson = (ResultJSON) ConvertUtil.bytes2Object(intent
					.getByteArrayExtra("resultJson"));
			listResult = resultJson.getListTraffic();
			if (listResult == null) {
				listResult = new ArrayList<ResultInput>();
			}

			// change show name to show number
			for (int i = 0; i < listResult.size(); i++) {
				String order = (i + 1) + "";
				listResult.get(i).setTrafficName(order);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		//
		lv = (ListView) findViewById(R.id.listResult);
		listResultAdapter = new ListResultArrayAdapter(this, R.layout.list_row,
				listResult);
		lv.setAdapter(listResultAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				View temView2 = listResultAdapter.getView(position, null, lv);
				TextView trafficID = (TextView) temView2
						.findViewById(R.id.trafficID);
				TrafficInfoJSON trafficInfoJSON = new TrafficInfoJSON();
				trafficInfoJSON = DBUtil.getTrafficDetail(trafficID.getText()
						.toString());
				byte[] dataBytes;
				try {
					// move next screen
					Intent nextScreen = new Intent(getApplicationContext(),
							TracfficSignDetailActivity.class);
					dataBytes = ConvertUtil.object2Bytes(trafficInfoJSON);
					nextScreen.putExtra("trafficDetails", dataBytes);
					startActivity(nextScreen);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		ImageView resultImage = (ImageView) findViewById(R.id.imageResult);
		Bitmap image = ImageUtils.drawImage(imagePath, listResult);
		resultImage.setImageBitmap(image);
	}

	@Override
	public void onResume() {
		super.onResume();
		mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
		// OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this,
		// mLoaderCallback);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.feedback_menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == R.id.action_feedback){
			Intent nextScreen = new Intent(getApplicationContext(),FeedbackActivity.class);
			nextScreen.putExtra("feedbackType", "1");
			nextScreen.putExtra("referenceID", resultJson.getResultID());
			startActivity(nextScreen);
		}
		return super.onOptionsItemSelected(item);
	}

	// @Override
	// protected void onStart() {
	// // TODO Auto-generated method stub
	// super.onStart();
	// // display image
	// for (int i = 0; i < listResult.size(); i++) {
	// try {
	// int wantedPosition = i;
	// if (listResult.get(wantedPosition).getTrafficID() != null
	// && listResult.get(wantedPosition).getTrafficID()
	// .length() > 0) {
	// HttpImageUtils httpImgUtil = new HttpImageUtils();
	// httpImgUtil.setUrl(serviceIp
	// + listResult.get(wantedPosition).getTrafficImage()
	// + "?size=small");
	// httpImgUtil.setExtra(wantedPosition);
	// httpImgUtil
	// .setHttpImageListener(new IAsyncHttpImageListener() {
	//
	// @Override
	// public void onComplete(Bitmap bitmap, int extra) {
	// try {
	// // View tmpView =
	// // listResultAdapter.getView(
	// // extra, null, lv);
	// View tmpView = lv.getChildAt(extra);
	// ImageView icon = (ImageView) tmpView
	// .findViewById(R.id.trafficImage);
	// icon.setImageBitmap(bitmap);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// httpImgUtil.execute();
	// }// end if length > 0
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }// end for

	// }
	// @Override
	// protected void onResume() {
	// //Toast.makeText(getApplicationContext(), "OnResume",
	// Toast.LENGTH_SHORT);
	// // display image
	// // for (int i = 0; i < arr2.size(); i++) {
	// // int wantedPosition = i;
	// // if (arr2.get(wantedPosition).getTrafficID().length() > 0) {
	// // HttpImageUtils httpImgUtil = new HttpImageUtils();
	// // httpImgUtil.setUrl(serverAddress
	// // + arr2.get(wantedPosition).getTrafficImage());
	// // httpImgUtil.setExtra(wantedPosition);
	// // httpImgUtil.setHttpImageListener(new IAsyncHttpImageListener() {
	// //
	// // @Override
	// // public void onComplete(Bitmap bitmap, int extra) {
	// // // Bitmap tempBit = null;
	// // View tmpView = lv.getChildAt(extra);// myArray.getView(0,
	// // // null,
	// // // lv);
	// // ImageView icon = (ImageView) tmpView
	// // .findViewById(R.id.trafficImage);
	// // icon.setImageBitmap(bitmap);
	// // }
	// // });
	// // httpImgUtil.execute();
	// // }// end if length > 0
	// // }// end for
	// }
}
