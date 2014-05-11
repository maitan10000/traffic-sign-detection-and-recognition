package utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class ResultExtraUtil {

	/**
	 * Check result is read or not
	 * 
	 * @param id
	 * @return
	 */
	public static Boolean isRead(int id) {
		String readFile = GlobalValue.getConfigPath().replace(
				"config.properties", "readFile.txt");
		String idString = id + "";
		try {
			ArrayList<String> lines = (ArrayList<String>) FileUtils
					.readLines(new File(readFile));
			for (String line : lines) {
				if (idString.equals(line)) {
					return true;
				}
			}
		} catch (IOException e) {
		}
		return false;
	}

	public static Boolean addRead(int id) {
		String readFile = GlobalValue.getConfigPath().replace(
				"config.properties", "readFile.txt");
		String idString = id + "";
		try {
			ArrayList<String> lines = (ArrayList<String>) FileUtils
					.readLines(new File(readFile));
			for (String line : lines) {
				if (idString.equals(line)) {
					return false;
				}
			}
		} catch (IOException e) {
		}

		try {
			// lines.add(idString);
			// FileUtils.writeLines(new File(readFile), lines);
			FileUtils.writeStringToFile(new File(readFile), idString + "\n",
					true);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
