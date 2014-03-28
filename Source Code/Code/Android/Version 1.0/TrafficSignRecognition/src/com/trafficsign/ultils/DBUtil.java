package com.trafficsign.ultils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.trafficsign.activity.MainActivity;
import com.trafficsign.activity.R;
import com.trafficsign.json.CategoryJSON;
import com.trafficsign.json.ResultDB;
import com.trafficsign.json.TrafficInfoJSON;
import com.trafficsign.json.TrafficInfoShortJSON;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class DBUtil {
	// create folder for contain DB and inamge if not exist
	public static void initResource(InputStream dbIS, InputStream settingIS,
			Context ctx) {
		String externalPath = Environment.getExternalStorageDirectory()
				.getPath() + "/";
		try {
			GlobalValue.createInstance(externalPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File appFolder = new File(GlobalValue.getAppFolder());
		if (!appFolder.exists()) {
			appFolder.mkdir();
		}

		File saveImageFolder = new File(GlobalValue.getAppFolder()
				+ Properties.SAVE_IMAGE_FOLDER);
		if (!saveImageFolder.exists()) {
			saveImageFolder.mkdir();
		}

		File mainImageFolder = new File(GlobalValue.getAppFolder()
				+ Properties.MAIN_IMAGE_FOLDER);
		if (!mainImageFolder.exists()) {
			mainImageFolder.mkdir();
		}

		File dbFile = new File(GlobalValue.getAppFolder()
				+ Properties.DB_FILE_PATH);
		if (!dbFile.exists()) {
			DBUtil.copyDB(dbIS, dbFile);
			HttpDatabaseUtil httpDB = new HttpDatabaseUtil(ctx);
			httpDB.execute();
		}

		File settingFile = new File(GlobalValue.getAppFolder()
				+ Properties.SETTING_FILE_PATH);
		if (!settingFile.exists()) {
			DBUtil.copyDB(settingIS, settingFile);
		}

	}

	// coppy file from res to folder in sdCard
	public static void copyDB(InputStream in, File dst) {
		try {
			OutputStream out = new FileOutputStream(dst);

			// Transfer bytes from in to out
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// insert Category
	public static long insertCategory(CategoryJSON categoryJSON) {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				GlobalValue.getAppFolder() + Properties.DB_FILE_PATH, null,
				SQLiteDatabase.OPEN_READWRITE);
		ContentValues values = new ContentValues();
		values.put("categoryID", categoryJSON.getCategoryID());
		values.put("categoryName", categoryJSON.getCategoryName());
		Long dbReturn = db.insert("category", null, values);
		db.close();
		return dbReturn;
	}

	// insert traffic sign
	public static long insertTraffic(TrafficInfoJSON trafficInfoJSON) {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				GlobalValue.getAppFolder() + Properties.DB_FILE_PATH, null,
				SQLiteDatabase.OPEN_READWRITE);
		ContentValues values = new ContentValues();
		values.put("trafficID", trafficInfoJSON.getTrafficID());
		values.put("name", trafficInfoJSON.getName());
		values.put("image", trafficInfoJSON.getImage());
		values.put("categoryID", trafficInfoJSON.getCategoryID());
		values.put("information", trafficInfoJSON.getInformation());
		values.put("penaltyfee", trafficInfoJSON.getPenaltyfee());
		// insert to DB
		Long dbReturn = db.insert("trafficinformation", null, values);
		db.close();
		return dbReturn;
	}

	// get all category
	public static ArrayList<CategoryJSON> getAllCategory() {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				GlobalValue.getAppFolder() + Properties.DB_FILE_PATH, null,
				SQLiteDatabase.OPEN_READWRITE);
		// create cursor to access query result (select * from "category")
		Cursor cursor = db
				.query("category", null, null, null, null, null, null);
		cursor.moveToFirst();
		ArrayList<CategoryJSON> listCategory = new ArrayList<CategoryJSON>();
		// move cursor to first and check if cursor is null
		if (cursor.moveToFirst()) {
			// loop for get all category to list
			do {
				CategoryJSON temp = new CategoryJSON();
				temp.setCategoryID(cursor.getString(cursor
						.getColumnIndexOrThrow("categoryID")));
				temp.setCategoryName(cursor.getString(cursor
						.getColumnIndexOrThrow("categoryName")));
				listCategory.add(temp);
			} while (cursor.moveToNext());
		}

		db.close();
		return listCategory;

	}

	// get listTraffic by categoryID return arrayList trafficInfoShort
	public static ArrayList<TrafficInfoShortJSON> getTrafficByCategory(
			String categoryID) {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				GlobalValue.getAppFolder() + Properties.DB_FILE_PATH, null,
				SQLiteDatabase.OPEN_READWRITE);
		// create cursor to access query result (SELECT * FROM
		// `trafficinformation` WHERE `categoryID` = ...)
		Cursor cursor = db.query("trafficinformation", null, " `categoryID` ="
				+ categoryID, null, null, null, null);
		ArrayList<TrafficInfoShortJSON> listTrafficInfoShortJSONs = new ArrayList<TrafficInfoShortJSON>();
		String imagePath = "";
		// move cursor to first and check if cursor is null
		if (cursor.moveToFirst()) {
			do {

				TrafficInfoShortJSON temp = new TrafficInfoShortJSON();
				temp.setCategoryID(Integer.parseInt(cursor.getString(cursor
						.getColumnIndexOrThrow("categoryID"))));
				temp.setName(cursor.getString(cursor
						.getColumnIndexOrThrow("name")));
				temp.setTrafficID(cursor.getString(cursor
						.getColumnIndexOrThrow("trafficID")));
				/*
				 * get image Address by get path in properties class and plus
				 * with trafficID
				 */
				imagePath = GlobalValue.getAppFolder()
						+ Properties.MAIN_IMAGE_FOLDER + temp.getTrafficID()
						+ ".jpg";
				temp.setImage(imagePath);
				// add temp to list traffic
				listTrafficInfoShortJSONs.add(temp);
			} while (cursor.moveToNext());
		}

		db.close();
		return listTrafficInfoShortJSONs;
	}

	// get listTraffic by searchKey return arrayList trafficInfoShort
	public static ArrayList<TrafficInfoShortJSON> getTrafficByName(
			String searchKey) {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				GlobalValue.getAppFolder() + Properties.DB_FILE_PATH, null,
				SQLiteDatabase.OPEN_READWRITE);
		// create cursor to access query result (SELECT * FROM
		// `trafficinformation` WHERE `name` LIKE '%...%')
		Cursor cursor = db.query("trafficinformation", null, " `name` LIKE '%"
				+ searchKey + "%'", null, null, null, null);
		ArrayList<TrafficInfoShortJSON> listTrafficInfoShortJSONs = new ArrayList<TrafficInfoShortJSON>();
		String imagePath = "";
		// move cursor to first and check if cursor is null
		if (cursor.moveToFirst()) {
			do {

				TrafficInfoShortJSON temp = new TrafficInfoShortJSON();
				temp.setCategoryID(Integer.parseInt(cursor.getString(cursor
						.getColumnIndexOrThrow("categoryID"))));
				temp.setName(cursor.getString(cursor
						.getColumnIndexOrThrow("name")));
				temp.setTrafficID(cursor.getString(cursor
						.getColumnIndexOrThrow("trafficID")));
				/*
				 * get image Address by get path in properties class and plus
				 * with trafficID
				 */
				imagePath = GlobalValue.getAppFolder()
						+ Properties.MAIN_IMAGE_FOLDER + temp.getTrafficID()
						+ ".jpg";
				temp.setImage(imagePath);
				// add temp to list traffic
				listTrafficInfoShortJSONs.add(temp);
			} while (cursor.moveToNext());
		}

		db.close();
		return listTrafficInfoShortJSONs;
	}

	// get traffic details by traffic ID
	public static TrafficInfoJSON getTrafficDetail(String trafficID) {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				GlobalValue.getAppFolder() + Properties.DB_FILE_PATH, null,
				SQLiteDatabase.OPEN_READWRITE);
		// create cursor to access query result (SELECT * FROM
		// `trafficinformation` WHERE `name` LIKE '%...%')
		Cursor cursor = db
				.query("trafficinformation", null, " `trafficID` LIKE '"
						+ trafficID + "'", null, null, null, null);
		TrafficInfoJSON trafficInfoJSON = new TrafficInfoJSON();
		// move cursor to first and check if cursor is null
		if (cursor.moveToFirst()) {
			// get traffic details from cursor to Object
			trafficInfoJSON.setTrafficID(cursor.getString(cursor
					.getColumnIndexOrThrow("trafficID")));
			trafficInfoJSON.setName(cursor.getString(cursor
					.getColumnIndexOrThrow("name")));
			trafficInfoJSON.setCategoryID(Integer.parseInt(cursor
					.getString(cursor.getColumnIndexOrThrow("categoryID"))));
			trafficInfoJSON.setInformation(cursor.getString(cursor
					.getColumnIndexOrThrow("information")));
			trafficInfoJSON.setPenaltyfee(cursor.getString(cursor
					.getColumnIndexOrThrow("penaltyfee")));
			// set image Path
			String imagePath = GlobalValue.getAppFolder()
					+ Properties.MAIN_IMAGE_FOLDER
					+ trafficInfoJSON.getTrafficID() + ".jpg";
			trafficInfoJSON.setImage(imagePath);
		}

		return trafficInfoJSON;
	}

	// insert to result table for autosearch later
	public static boolean saveSearchInfo(String picturePath, String locateJSON) {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				GlobalValue.getAppFolder() + Properties.DB_FILE_PATH, null,
				SQLiteDatabase.OPEN_READWRITE);
		// set values to insert
		ContentValues values = new ContentValues();
		values.put("resultID", -1);
		values.put("uploadedImage", picturePath);
		values.put("listTraffic", locateJSON);
		values.put("creator", Properties.USER_NAME);
		// get current datetime
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		Calendar cal = Calendar.getInstance();
		values.put("createDate", dateFormat.format(cal.getTime()));
		values.put("isActive", true);
		if (db.insert("result", null, values) != -1) {
			return true;
		}
		return false;
	}

	// get autoSerch from result table for upload and excute search
	public static ArrayList<ResultDB> getSavedSearch() {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				GlobalValue.getAppFolder() + Properties.DB_FILE_PATH, null,
				SQLiteDatabase.OPEN_READWRITE);
		// create cursor to access query result
		Cursor cursor = db.query("result", null, "`resultID` = -1", null,
				null, null, null);
		ArrayList<ResultDB> listResult = new ArrayList<ResultDB>();
		// move cursor to first and check if cursor is null
		if (cursor.moveToFirst()) {
			// get result from cursor to Object
			do {
				ResultDB temp = new ResultDB();
				temp.setUploadedImage(cursor.getString(cursor
						.getColumnIndexOrThrow("uploadedImage")));
				temp.setLocate(cursor.getString(cursor
						.getColumnIndexOrThrow("listTraffic")));
				temp.setCreator(cursor.getString(cursor
						.getColumnIndexOrThrow("creator")));
				listResult.add(temp);
			} while (cursor.moveToNext());

		}
		return listResult;
	}

}
