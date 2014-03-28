package com.trafficsign.ultils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



public class GlobalValue {
	private static String appFolder = "";
	private static String serviceAddress = "";
	private static boolean isCreated = false;

	public static void createInstance(String externalPath) throws Exception {
		if (isCreated == false) {
			isCreated = true;
			//Properties prop = new Properties();
			//FileInputStream in;
//			try {
				//in = new FileInputStream(configPath);
				//prop.load(in);
				
				//map value
				appFolder = externalPath + Properties.APP_FOLDER;//prop.getProperty("appFolder").trim();
				serviceAddress =  Properties.serviceIp;
				
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} else {
			throw new Exception("Cannot recreate new instance");
		}
	}

	public static String getAppFolder() {
		return appFolder;
	}

	public static String getServiceAddress() {
		return serviceAddress;
	}
	

}
