package com.trafficsign.ultils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trafficsign.json.ResultShortJSON;

public class Helper {
	// check email
	public static boolean isEmail(String email) {
		if (email == null) {
			return false;
		} else {
			return email.contains(" ") == false
					&& email.matches(".+@.+\\.[a-z]+");
		}

	}

	// coppy file
	public static void copyFileUsingStream(String sourcePath, String destPath) {
		File source = new File(sourcePath);
		File dest = new File(destPath);
		InputStream is = null;
		OutputStream os = null;
		try {
			try {
				is = new FileInputStream(source);
				os = new FileOutputStream(dest);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	// get properties file
	public static String getProperty(String path, String key) {
		Properties prop = new Properties();
		InputStream input = null;
		String output = "";
		try {
			input = new FileInputStream(path);
			prop.load(input);
			output = prop.getProperty(key, "");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return output;
	}

	// write properties
	public static void writeProperty(String path, String key, String value) {
		Properties prop = new Properties();
		OutputStream output = null;

		try {
			output = new FileOutputStream(path);
			// set the properties value
			prop.setProperty(key, value);
			// save properties
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static <T> T fromJson(String jsonString, Type typeOfT) {
		jsonString = jsonString.replace("ICT", "GMT+07:00");
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG,
				DateFormat.LONG).create();
		return gson.fromJson(jsonString, typeOfT);
	}

	public static String toJson(Object object) {
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG,
				DateFormat.LONG).create();
		String jsonString = gson.toJson(object);
		return jsonString.replace("GMT+07:00", "ICT");
	}

	//Date to String	
	public static String dateToString(Date date)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		return dateFormat.format(date);
	}
}
