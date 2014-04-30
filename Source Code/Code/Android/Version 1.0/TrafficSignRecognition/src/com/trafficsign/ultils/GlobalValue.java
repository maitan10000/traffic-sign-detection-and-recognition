package com.trafficsign.ultils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;



public class GlobalValue {
	private static String appFolder = "";
	private static String serviceAddressOnline = "";
	private static String serviceAddressLocal = "";
	private static boolean isCreated = false;
	
	public static boolean isUploading = false;

	public static void createInstance(String externalPath) throws Exception {
		if (isCreated == false) {
			isCreated = true;
				appFolder = externalPath + Properties.APP_FOLDER;
				serviceAddressOnline =  Properties.serviceIpOnline;
				serviceAddressLocal =  Properties.serviceIpLocal;
		} else {
			throw new Exception("Cannot recreate new instance");
		}
	}

	public static String getAppFolder() {
		
		return appFolder;
	}

	public static String getServiceAddress() {
		String address = Helper.getProperty(getAppFolder() + Properties.SETTING_FILE_PATH, Properties.PROPERTIES_KEY_SERVER);;
		return address;
//		type = Helper.getProperty(getAppFolder() + Properties.SETTING_FILE_PATH, Properties.PROPERTIES_KEY_SERVER);
//		if(Properties.PROPERTIES_VALUE_ONLINE.equals(type)){
//			return serviceAddressOnline;
//		} else {
//			return serviceAddressLocal;
//		}
		
		
	}
	

}
