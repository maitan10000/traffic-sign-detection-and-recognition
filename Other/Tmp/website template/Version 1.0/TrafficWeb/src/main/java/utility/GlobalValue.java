package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class GlobalValue {
	private static String serviceAddress = "";
	private static boolean isCreated = false;

	public static void createInstance(String configPath) throws Exception {
		if (isCreated == false) {
			isCreated = true;
			Properties prop = new Properties();
			FileInputStream in;
			try {
				in = new FileInputStream(configPath);
				prop.load(in);
				
				//map value
				serviceAddress = prop.getProperty("ServiceAddress").trim();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new Exception("Cannot recreate new instance");
		}
	}

	public static String getServiceAddress() {
		return serviceAddress;
	}
}
