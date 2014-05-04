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
	private static String serviceAddress = "";
	private static boolean showFPS = false;
	
	public static boolean isUploading = false;
	public static boolean isUpdating = false;
	
	public static void initAppFolder(String externalPath)
	{
		appFolder = externalPath + Properties.APP_FOLDER;
	}
	
	public static void createInstance() throws Exception {
		if (isCreated == false) {
			isCreated = true;			
			try {
				String propertyFile = getAppFolder()
						+ Properties.SETTING_FILE_PATH;
				java.util.Properties prop = new java.util.Properties();
				InputStream input = new FileInputStream(propertyFile);
				prop.load(input);

				serviceAddress = prop.getProperty("ServiceAddress");

				try {
					showFPS = Boolean.parseBoolean(prop.getProperty("ShowFPS"));
				} catch (Exception ex) {
					ex.printStackTrace();
				}

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

	public static boolean isShowFPS() {
		return showFPS;
	}
	
	

}
