package com.trafficsign.activity;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.core.*;

import com.google.gson.reflect.TypeToken;
import com.trafficsign.activity.R;
import com.trafficsign.json.LocateJSON;
import com.trafficsign.json.ResultDB;
import com.trafficsign.json.ResultInput;
import com.trafficsign.json.ResultJSON;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.ultils.ConvertUtil;
import com.trafficsign.ultils.DBUtil;
import com.trafficsign.ultils.GlobalValue;
import com.trafficsign.ultils.Helper;
import com.trafficsign.ultils.HttpUtil;
import com.trafficsign.ultils.MyInterface.IUploadProgressListener;
import com.trafficsign.ultils.NetUtil;
import com.trafficsign.ultils.Properties;
import com.trafficsign.ultils.UploadUtils;
import com.trafficsign.view.CameraView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;
import static com.trafficsign.ultils.Properties.*;

public class CameraActivity extends Activity implements CvCameraViewListener2,
		OnTouchListener {
	ProgressDialog progressDialog;
	private int networkFlag;
	private boolean isAuto = false;
	private boolean flag = false;
	private String jsonString = "";
	private static final String TAG = "TSRT::Activity";
	private CameraView mOpenCvCameraView;
	private List<Size> mResolutionList;
	private MenuItem[] mResolutionMenuItems;
	private SubMenu mResolutionMenu;
	private CascadeClassifier type1Detector = null;
	private CascadeClassifier type2Detector = null;
	private int blinkCount = 0;
	private ImageButton btnTakeImage;
	private ImageButton btnUpload;
	private ProgressBar progressBar;
	private String user = "";
	private Long timeAuto = (long) 3;

	static {
		if (!OpenCVLoader.initDebug())
			Log.d("debug", "Unable to load OpenCV");
		else
			Log.d("debug", "OpenCV loaded");
	}

	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS: {
				// Log.i(TAG, "OpenCV loaded successfully");
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

	String fileName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// get network setting
		final SharedPreferences prefSetting = getSharedPreferences(
				Properties.SHARE_PREFERENCE_SETTING, Context.MODE_PRIVATE);
		boolean wifiStatus = prefSetting.getBoolean(
				Properties.SHARE_PREFERENCE_KEY_WIFI, true);
		networkFlag = 1;
		if (wifiStatus == false) {
			networkFlag = 0;
		}
		// get user
		final SharedPreferences prefLogin = getSharedPreferences(
				Properties.SHARE_PREFERENCE_LOGIN, Context.MODE_PRIVATE);
		user = prefLogin.getString(Properties.SHARE_PREFERENCE__KEY_USER, "");
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_camera);
		// get time auto
		timeAuto = prefSetting.getLong(
				Properties.SHARE_PREFERENCE__KEY_SEARCH_AUTO_TIME, 3);
		//
		mOpenCvCameraView = (CameraView) findViewById(R.id.cameraVIew);
		mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
		mOpenCvCameraView.setCvCameraViewListener(this);
		Log.i(TAG, "Create successully");
		// set event for upload button
		btnUpload = (ImageButton) findViewById(R.id.upload);
		btnUpload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, 1);
			}
		});
		// set event onclick for button take
		btnTakeImage = (ImageButton) findViewById(R.id.take);
		btnTakeImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(isTaken == false && isAuto == false)
				{
					isTaken = true;
					Toast.makeText(getApplicationContext(), "Chụp bằng tay!",
						Toast.LENGTH_SHORT).show();
					captureAndUploadImage(listLocate);
				}

			}
		});

		progressBar = (ProgressBar) findViewById(R.id.progressBarCamera);
	}

	// on event get selected image ok
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (resultCode == RESULT_OK) {
				if (requestCode == 1) {
					Uri selectedImageUri = data.getData();

					String selectedImagePath = getPath(selectedImageUri);
					Intent nextScreen = new Intent(getApplicationContext(),
							ImageReviewActivity.class);
					nextScreen.putExtra("imagePath", selectedImagePath);
					finish();
					startActivity(nextScreen);

				}
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Có lỗi xảy ra, vui lòng thử lại sau", Toast.LENGTH_SHORT)
					.show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.camera_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.byHand: {
			isAuto = false;
			item.setChecked(true);

		}
		case R.id.auto: {
			isAuto = true;
			item.setChecked(true);

		}
		}
		return true;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
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
		isTaken = false;
		mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
		// OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this,
		// mLoaderCallback);
		final ToggleButton btnAuto = (ToggleButton) findViewById(R.id.cameraType);
		isAuto = btnAuto.isChecked();
		btnAuto.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				isAuto = btnAuto.isChecked();
			}
		});
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

	Mat frame;
	Mat saveFrame;
	ArrayList<Rect> listLocate;
	long starTime = 0;
	long autoCaptureCount = 0;
	int detectedCount = 0;

	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		if (isTaken == false) {
			frame = inputFrame.rgba();
		} else {
			frame = Highgui.imread(fileName, Highgui.CV_LOAD_IMAGE_COLOR);
			Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGB2BGRA);
		}
		if (frame.empty()) {
			Log.e(TAG, "Failed to load image");
		}
		saveFrame = frame.clone();

		// Detect traffic sign
		if (type1Detector == null || type1Detector.empty()) {
			// Init cascade to detect
			InputStream is = getResources()
					.openRawResource(R.raw.cascade_type1);
			File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
			File mCascadeFile = new File(cascadeDir, "cascade_type1.xml");
			try {
				FileOutputStream os = new FileOutputStream(mCascadeFile);
				byte[] buffer = new byte[4096];
				int bytesRead;
				while ((bytesRead = is.read(buffer)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				is.close();
				os.close();

				type1Detector = new CascadeClassifier(
						mCascadeFile.getAbsolutePath());
				if (type1Detector != null) {
					Log.i(TAG, "Load cascade successfully");
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		if (type2Detector == null || type2Detector.empty()) {
			// Init cascade to detect
			InputStream is = getResources()
					.openRawResource(R.raw.cascade_type2);
			File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
			File mCascadeFile = new File(cascadeDir, "cascade_type2.xml");
			try {
				FileOutputStream os = new FileOutputStream(mCascadeFile);
				byte[] buffer = new byte[4096];
				int bytesRead;
				while ((bytesRead = is.read(buffer)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				is.close();
				os.close();

				type2Detector = new CascadeClassifier(
						mCascadeFile.getAbsolutePath());
				if (type2Detector != null) {
					Log.i(TAG, "Load cascade successfully");
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// Begin detect
		{
			listLocate = new ArrayList<Rect>();
			Rect[] tempResult = detectTS(type1Detector, frame);
			// copy result

			for (int i = 0; i < tempResult.length; i++) {
				listLocate.add((Rect) tempResult[i]);
			}

			tempResult = detectTS(type2Detector, frame);
			for (int i = 0; i < tempResult.length; i++) {
				listLocate.add((Rect) tempResult[i]);
			}

			// Draw detect area
			for (int i = 0; i < listLocate.size(); i++) {
				Core.rectangle(frame, listLocate.get(i).tl(), listLocate.get(i)
						.br(), new Scalar(204, 51, 204), 3);
			}

			// draw benchmark frame
			long runTime = 0;
			if (starTime != 0) {
				runTime = SystemClock.uptimeMillis() - starTime;
			}

			if (GlobalValue.isShowFPS() == true && isTaken == false) {
				float framePerSencond = ((float) 1000) / runTime;
				DecimalFormat df = new DecimalFormat("#.00");
				Point point = new Point();
				point.x = 30;
				point.y = 30;
				Core.putText(frame, df.format(framePerSencond) + "fps", point,
						1, 2, new Scalar(204, 51, 204), 2);
			}
			starTime = SystemClock.uptimeMillis();

			// Auto take picture and submit
			if (isAuto == true && detectedCount > 0) {
				autoCaptureCount += runTime;
				runOnUiThread(new Runnable() {
					public void run() {
						progressBar.setVisibility(View.VISIBLE);
					}
				});
			} else {
				autoCaptureCount = 0;
				runOnUiThread(new Runnable() {
					public void run() {
						progressBar.setVisibility(View.GONE);
					}
				});
			}

			if (detectedCount != listLocate.size()) {
				detectedCount = listLocate.size();
				autoCaptureCount = 0;
			}

			// show progress bar when auto capture ON
			if (isTaken == false) {
				int percent = (int) (autoCaptureCount * 100 / (timeAuto * 1000));
				progressBar.setProgress(percent);
			}

			if (detectedCount > 0 && autoCaptureCount > timeAuto * 1000
					&& isTaken == false && isAuto == true) {
				isTaken = true;
				autoCaptureCount = 0;
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(),
								"Chụp tự động!", Toast.LENGTH_SHORT).show();
					}
				});
				captureAndUploadImage(listLocate);
			}
		}

		return frame;
	}

	// detect traffic sign
	//
	Rect[] detectTS(CascadeClassifier detector, Mat frameInput) {
		MatOfRect results = new MatOfRect();
		org.opencv.core.Size minSize = new org.opencv.core.Size();
		minSize.height = frameInput.width() / 10;
		minSize.width = minSize.height;

		org.opencv.core.Size maxSize = new org.opencv.core.Size();
		maxSize.height = frameInput.width() / 4;
		maxSize.width = maxSize.height;	
		try {
			detector.detectMultiScale(frameInput, results, 1.1, 1, 0 | 2,
					minSize, maxSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results.toArray();
	}

	// capture and upload image
	void captureAndUploadImage(final ArrayList<Rect> listLocateInput) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String currentDateandTime = sdf.format(new Date());
		fileName = GlobalValue.getAppFolder() + Properties.SAVE_IMAGE_FOLDER
				+ currentDateandTime + ".jpg";

		// mOpenCvCameraView.takePicture(fileName);
		if (!saveFrame.empty()) {
			Imgproc.cvtColor(saveFrame, saveFrame, Imgproc.COLOR_RGB2BGRA);
			Highgui.imwrite(fileName, saveFrame);
		}

		runOnUiThread(new Runnable() {
			public void run() {
				// Create dialog
				progressDialog = new ProgressDialog(CameraActivity.this);
				progressDialog.setMessage("Vui lòng đợi trong giây lát");
				progressDialog.setCancelable(false);
			}
		});
		// upload file ****************************
		final String upLoadServerUri = GlobalValue.getServiceAddress()
				+ Properties.TRAFFIC_SEARCH_AUTO;
		new Thread(new Runnable() {
			public void run() {
				try {
					runOnUiThread(new Runnable() {
						public void run() {
							if (progressDialog != null) {
								progressDialog.show();
							}
						}
					});

					Thread.sleep(1000);

					// create post data
					ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
					parameters.add(new BasicNameValuePair("userID", user));
					ArrayList<ResultInput> tempListResultInput = new ArrayList<ResultInput>();
					for (Rect rect : listLocateInput) {
						ResultInput tempResultInput = new ResultInput();
						LocateJSON locate = new LocateJSON();
						locate.setHeight(rect.height);
						locate.setWidth(rect.width);
						locate.setY(rect.y);
						locate.setX(rect.x);
						tempResultInput.setLocate(locate);
						tempListResultInput.add(tempResultInput);
					}
					// JSON string listlocate
					String listLocateJSON = Helper.toJson(tempListResultInput);
					// if access to server ok
					if (NetUtil.networkState(CameraActivity.this) > networkFlag) {
						parameters.add(new BasicNameValuePair("listLocate",
								listLocateJSON));
						UploadUtils uploader = new UploadUtils();
						progressBar.setProgress(0);
						uploader.setUploadListener(new IUploadProgressListener() {
							@Override
							public void onProgress(int percent) {
								progressBar.setProgress(percent);
								// Log.e(TAG, "Upload percent: "+percent);
							}

							@Override
							public void onComplete() {
								progressBar.setProgress(100);
							}
						});

						jsonString = uploader.uploadFileWithProgress(fileName,
								upLoadServerUri, parameters);

						Log.e("jsonUpload", jsonString);
						if (jsonString.contains("null")
								|| jsonString.trim().isEmpty()) {
							runOnUiThread(new Runnable() {
								public void run() {
									if (isAuto == false) {
										Toast.makeText(getApplicationContext(),
												jsonString, Toast.LENGTH_LONG)
												.show();
									}
								}
							});

						} else {

							Log.i(TAG, "JsonString: " + jsonString);
							ResultJSON resultJson = new ResultJSON();
							resultJson = Helper.fromJson(jsonString,
									ResultJSON.class);
							if (resultJson != null
									&& resultJson.getListTraffic().size() == 0) {
								runOnUiThread(new Runnable() {
									public void run() {
										if (isAuto == false) {
											Toast.makeText(
													getApplicationContext(),
													"Không có kết quả",
													Toast.LENGTH_LONG).show();
										}
									}
								});
							}

							// ResultInput resultTmp = resultInput.get(0);
							// save result to db in mobile
							ResultDB resultDB = new ResultDB();
							resultDB.setCreateDate(resultJson.getCreateDate());
							resultDB.setCreator(resultJson.getCreator());
							resultDB.setLocate(Helper.toJson(resultJson
									.getListTraffic()));
							resultDB.setResultID(resultJson.getResultID());
							resultDB.setUploadedImage(fileName);
							DBUtil.addResult(resultDB, user);
							// check if there are traffic sign in result do not
							// have in DB
							ArrayList<ResultInput> listResult = resultJson
									.getListTraffic();
							if (listResult != null && listResult.size() > 0) {
								for (int i = 0; i < listResult.size(); i++) {
									final String urlGetTrafficDetail = GlobalValue
											.getServiceAddress()
											+ Properties.TRAFFIC_TRAFFIC_VIEW
											+ "?id=";
									if (listResult.get(i).getTrafficID() != null
											&& DBUtil.checkTraffic(listResult
													.get(i).getTrafficID()) == false) {
										String urlGetTrafficDetailFull = urlGetTrafficDetail
												+ listResult.get(i)
														.getTrafficID();
										// get traffic detail from service and
										// parse json
										// TrafficInfoJson
										String trafficJSON = HttpUtil
												.get(urlGetTrafficDetailFull);
										TrafficInfoJSON trafficInfoJSON = new TrafficInfoJSON();
										trafficInfoJSON = Helper.fromJson(
												trafficJSON,
												TrafficInfoJSON.class);
										// add traffic to DB
										if (DBUtil.checkTraffic(trafficInfoJSON
												.getTrafficID()) == false) {
											DBUtil.insertTraffic(trafficInfoJSON);

										}
										String savePath = GlobalValue
												.getAppFolder()
												+ Properties.MAIN_IMAGE_FOLDER
												+ trafficInfoJSON
														.getTrafficID()
												+ ".jpg";
										File image = new File(savePath);
										if (!image.exists()) {
											String imageLink = GlobalValue
													.getServiceAddress()
													+ trafficInfoJSON
															.getImage();
											if (HttpUtil.downloadImage(
													imageLink, savePath)) {
												Log.e("DB Image", savePath);
											}

										}
									}
								}
							}
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
								runOnUiThread(new Runnable() {
									public void run() {
										if (progressDialog != null) {
											progressDialog.dismiss();
										}
									}
								});

								// isTaken = false;
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

						// save searchInfo to DB
						DBUtil.saveSearchInfo(fileName, listLocateJSON, user);
						Intent nextScreen = new Intent(getApplicationContext(),
								MainActivity.class);
						runOnUiThread(new Runnable() {
							public void run() {
								if (progressDialog != null) {
									progressDialog.dismiss();
								}
								Toast.makeText(
										getApplicationContext(),
										"Không thể kết nổi tới máy chủ hoặc bị giới hạn gói dữ liệu. Kết quả sẽ được trả về sau",
										Toast.LENGTH_LONG).show();
							}
						});
						finish();
						startActivity(nextScreen);
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}

	/**
	 * helper to retrieve the path of an image URI
	 */
	public String getPath(Uri uri) {
		if (uri == null) {
			return null;
		}
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		return uri.getPath();
	}
}
