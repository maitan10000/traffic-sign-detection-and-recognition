package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GlobalValue {
	private static boolean isCreated = false;
	private static String workPath = "";
	private static String gmailUsername="";
	private static String gmailPassword ="";

	public static void createInstance(String configPath) throws Exception {
		if (isCreated == false) {
			Properties prop = new Properties();
			FileInputStream in;
			try {
				in = new FileInputStream(configPath);
				prop.load(in);
				// map value
				workPath = prop.getProperty("workPath").trim();
				gmailUsername = prop.getProperty("gmailUsername").trim();
				gmailPassword = prop.getProperty("gmailPassword").trim();

				java.net.InetAddress localMachine = java.net.InetAddress
						.getLocalHost();
				System.out.println("Hostname of local machine: "
						+ localMachine.getHostName());
				if (localMachine.getHostName().equals("everything-pc")) {
					workPath = prop.getProperty("workPath1").trim();
				}
				System.out.println("Work Path: " + workPath);
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

	public static String getWorkPath() {
		return workPath;
	}

	public static String getGmailUsername() {
		return gmailUsername;
	}

	public static String getGmailPassword() {
		return gmailPassword;
	}
	
}
