package com.example.trafficsignrecognition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.objdetect.CascadeClassifier;

import vo.ResultInput;

import com.example.ultils.ConvertUtil;
import com.example.ultils.JSONArrayParser;
import com.example.ultils.UploadUtils;
import com.example.view.CameraView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class CameraActivity extends Activity implements CvCameraViewListener2,
		OnTouchListener {

	private static final String TAG = "OpenCVDemo::Activity";
	private CameraView mOpenCvCameraView;
	private List<Size> mResolutionList;
	private MenuItem[] mResolutionMenuItems;
	private SubMenu mResolutionMenu;
	private CascadeClassifier warningDetector = null;
	private int blinkCount = 0;
	private ImageButton btnTakeImage;

	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS: {
				Log.i(TAG, "OpenCV loaded successfully");
				mOpenCvCameraView.enableView();
				mOpenCvCameraView.setOnTouchListener(CameraActivity.this);
			}
				break;
			default: {
				super.onManagerConnected(status);
			}
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_camera);

		mOpenCvCameraView = (CameraView) findViewById(R.id.cameraVIew);
		mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
		mOpenCvCameraView.setCvCameraViewListener(this);
		Log.i(TAG, "Create successully");

		btnTakeImage = (ImageButton) findViewById(R.id.take);
		btnTakeImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd_HH-mm-ss");
				String currentDateandTime = sdf.format(new Date());
				final String fileName = Environment
						.getExternalStorageDirectory().getPath()
						+ "/sample_picture_" + currentDateandTime + ".jpg";
				mOpenCvCameraView.takePicture(fileName);
				Toast.makeText(getApplicationContext(), fileName + " saved",
						Toast.LENGTH_SHORT).show();
				// testing upload file ****************************
				final String upLoadServerUri = "http://192.168.2.104:8090/Traffic1/rest/Service/upload";

				// dialog = ProgressDialog.show(UploadToServer.this, "",
				// "Uploading file...", true);

				new Thread(new Runnable() {
					public void run() {
						runOnUiThread(new Runnable() {
							public void run() {
								// messageText.setText("uploading started.....");
							}
						});
						try {
							Thread.sleep(2000);
							String jsonString = "";
							jsonString = UploadUtils.uploadFile(fileName,
									upLoadServerUri);
							/* parse json */
							try {
								JSONObject jsonObj = new JSONObject(jsonString);
								JSONArray jsonArr = jsonObj
										.getJSONArray("Result");
								ArrayList<ResultInput> resultInput = new ArrayList<ResultInput>();
								resultInput = ConvertUtil.parseToList(jsonArr);
								System.out.println("chieu dai mang ne :"
										+ resultInput.size());
								// create intent
								Intent nextScreen = new Intent(getApplicationContext(),
										ListResult.class);
								/* put resultInput to the next screen */
								byte[] dataBytes;
								try {
									dataBytes = ConvertUtil.object2Bytes(resultInput);
									System.out.println("Convert thanh cong");
									nextScreen.putExtra("listResult", dataBytes);
									System.out.println("Put thanh cong");
									startActivity(nextScreen);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								/* end put*/
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}).start();
			}
		});
		// end test upload file **************************************

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);

		mResolutionMenu = menu.addSubMenu("Resolution");
		mResolutionList = mOpenCvCameraView.getResolutionList();
		mResolutionMenuItems = new MenuItem[mResolutionList.size()];

		ListIterator<Size> resolutionItr = mResolutionList.listIterator();
		int idx = 0;
		while (resolutionItr.hasNext()) {
			Size element = resolutionItr.next();
			mResolutionMenuItems[idx] = mResolutionMenu.add(1, idx, Menu.NONE,
					Integer.valueOf(element.width).toString() + "x"
							+ Integer.valueOf(element.height).toString());
			idx++;
		}
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
		if (item.getGroupId() == 1) {
			int id = item.getItemId();
			Size resolution = mResolutionList.get(id);
			mOpenCvCameraView.setResolution(resolution);
			resolution = mOpenCvCameraView.getResolution();
			String caption = Integer.valueOf(resolution.width).toString() + "x"
					+ Integer.valueOf(resolution.height).toString();
			Toast.makeText(this, caption, Toast.LENGTH_SHORT).show();
		}

		return true;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		// Take picture
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		// String currentDateandTime = sdf.format(new Date());
		// String fileName = Environment.getExternalStorageDirectory().getPath()
		// + "/sample_picture_" + currentDateandTime + ".jpg";
		// mOpenCvCameraView.takePicture(fileName);
		// Toast.makeText(this, fileName + " saved", Toast.LENGTH_SHORT).show();
		return false;
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	@Override
	public void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this,
				mLoaderCallback);
	}

	public void onDestroy() {
		super.onDestroy();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	@Override
	public void onCameraViewStarted(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCameraViewStopped() {
		// TODO Auto-generated method stub

	}

	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		// TODO Auto-generated method stub

		Mat frame = inputFrame.rgba();

		// Detect traffic sign
		if (warningDetector == null || warningDetector.empty()) {
			Log.e(TAG, "Failed to load cascade classifier");
			// warningDetector = null;
			// Init cascade to detect
			InputStream is = getResources().openRawResource(R.raw.cascade);
			File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
			File mCascadeFile = new File(cascadeDir, "cascade.xml");
			try {
				FileOutputStream os = new FileOutputStream(mCascadeFile);
				byte[] buffer = new byte[4096];
				int bytesRead;
				while ((bytesRead = is.read(buffer)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				is.close();
				os.close();

				warningDetector = new CascadeClassifier(
						mCascadeFile.getAbsolutePath());
				if (warningDetector != null) {
					Log.i(TAG, "Load cascade successfully");
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// Log.i(TAG, "Loaded cascade classifier from ");
			MatOfRect results = new MatOfRect();
			warningDetector.detectMultiScale(frame, results);

			Rect[] resultArray = results.toArray();
			for (int i = 0; i < resultArray.length; i++)
				Core.rectangle(frame, resultArray[i].tl(), resultArray[i].br(),
						new Scalar(204, 51, 204), 3);
		}

		// Hint to help user capture image
		if (blinkCount == 0) {
			int width = frame.width();
			int height = frame.height();

			Rect hintRect = new Rect();
			hintRect.height = 100;
			hintRect.width = 100;
			hintRect.y = (height - hintRect.height) / 2;
			hintRect.x = (width - hintRect.width) / 2;
			Core.rectangle(frame, hintRect.tl(), hintRect.br(), new Scalar(255,
					255, 255), 2);
			blinkCount = 2;
		} else {
			blinkCount--;
		}

		return frame;
	}

}
