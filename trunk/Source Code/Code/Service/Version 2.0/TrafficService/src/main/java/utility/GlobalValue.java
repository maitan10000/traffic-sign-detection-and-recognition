package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class GlobalValue {
	private static String configPath = "";
	private static boolean isCreated = false;

	private static String connectionURL = "";
	private static String dbUser = "";
	private static String dbPassword = "";
	private static String workPath = "";
	private static String gmailUsername = "";
	private static String gmailPassword = "";
	private static int reTrainNum = 0;
	private static int activeDay = 1;

	public static int ReTrainCount = 0;
	public static boolean isReTraining = false;

	/**
	 * Load setting information to globalValue Only can create instance one time
	 * 
	 * @param configPath
	 * @throws Exception
	 */
	public static void createInstance(String configPath) throws Exception {
		GlobalValue.configPath = configPath;
		if (isCreated == false) {
			Properties prop = new Properties();
			FileInputStream in;
			try {
				in = new FileInputStream(configPath);
				prop.load(in);
				// map value
				connectionURL = prop.getProperty("connectionURL").trim();
				dbUser = prop.getProperty("dbUser").trim();
				dbPassword = prop.getProperty("dbPassword").trim();

				workPath = prop.getProperty("workPath").trim();
				gmailUsername = prop.getProperty("gmailUsername").trim();
				gmailPassword = prop.getProperty("gmailPassword").trim();
				try {
					reTrainNum = Integer.parseInt(prop
							.getProperty("reTrainNum").trim());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				try {
					activeDay = Integer.parseInt(prop.getProperty("activeDay")
							.trim());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

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

	/**
	 * Save setting to file
	 * 
	 * @return
	 */
	public static boolean saveSetting() {
		Properties prop = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(configPath);
			prop.load(in);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
				
		prop.setProperty("connectionURL", connectionURL);
		prop.setProperty("dbUser", dbUser);
		prop.setProperty("dbPassword", dbPassword);
		
		prop.setProperty("workPath", workPath);
		prop.setProperty("gmailUsername", gmailUsername);
		prop.setProperty("gmailPassword", gmailPassword);
		prop.setProperty("reTrainNum", reTrainNum + "");
		prop.setProperty("activeDay", activeDay+"");

		File f = new File(configPath);
		OutputStream out;
		try {
			out = new FileOutputStream(f);
			prop.store(out, "Config for Traffic Service");
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static String getConnectionURL() {
		return connectionURL;
	}

	public static String getDbUser() {
		return dbUser;
	}

	public static String getDbPasswork() {
		return dbPassword;
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

	public static int getReTrainNum() {
		return reTrainNum;
	}
	
	public static void setReTrainNum(int reTrainCount) {
		GlobalValue.reTrainNum = reTrainCount;
	}

	public static int getActiveDay() {
		return activeDay;
	}

	public static void setActiveDay(int activeDay) {
		GlobalValue.activeDay = activeDay;
	}
}
