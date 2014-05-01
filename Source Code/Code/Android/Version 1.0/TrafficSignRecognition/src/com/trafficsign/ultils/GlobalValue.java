package com.trafficsign.ultils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class GlobalValue {
	private static String appFolder = "";
	private static boolean isCreated = false;
	public static boolean isUploading = false;
	private static String serviceAddress = "";

	public static void createInstance(String externalPath) throws Exception {		
		if (isCreated == false) {
			isCreated = true;
			appFolder = externalPath + Properties.APP_FOLDER;
			try {
				String propertyFile = getAppFolder() + Properties.SETTING_FILE_PATH;
				java.util.Properties prop = new java.util.Properties();
				InputStream input = new FileInputStream(propertyFile);
				prop.load(input);
				
				serviceAddress = prop.getProperty("ServiceAddress");
				
				input.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String getAppFolder() {

		return appFolder;
	}

	public static String getServiceAddress() {
		return serviceAddress;
	}

}
