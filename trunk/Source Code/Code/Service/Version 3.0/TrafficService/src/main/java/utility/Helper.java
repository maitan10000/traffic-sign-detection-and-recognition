package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;

import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.STEditAs;

import json.ResultInput;

public class Helper {
	private static int tradeOffValue = 100;
	/**
	 * Run TSRT with list command
	 * 
	 * @param appDic
	 * @param listCommand
	 * @return
	 */
	public static String runTSRT(String appDic, ArrayList<String> listCommand) {
		String commands = "./TSRT";
		listCommand.add(0, commands);
		String result = executeCommand(appDic, listCommand);
		if (result.contains("Killed")) {
			result = trainSVMOnly(appDic, tradeOffValue);
			if (result.contains("done")) {
				result = "";
			}
		}
		return result;
	}

	/**
	 * Train DB using SVM
	 * 
	 * @param appDic
	 * @return
	 */
	public static String trainSVM(String appDic) {
		ArrayList<String> listCommand = new ArrayList<String>();
		listCommand.add("-l");
		listCommand.add(tradeOffValue+"");
		return runTSRT(appDic, listCommand);
	}

	public static String trainSVMOnly(String appDic, int tradeOffValue) {
		appDic += "Data/SVM_Multiclass";
		ArrayList<String> listCommand = new ArrayList<String>();
		listCommand.add("./svm_multiclass_learn");
		listCommand.add("-c");
		listCommand.add(tradeOffValue + "");
		listCommand.add("saveFeatures.txt");
		listCommand.add("model.txt");
		return executeCommand(appDic, listCommand);
	}

	private static String executeCommand(String appDic,
			ArrayList<String> listCommand) {
		// Run macro on target
		ProcessBuilder pb = new ProcessBuilder();
		pb.command(listCommand);
		pb.directory(new File(appDic));
		pb.redirectErrorStream(true);
		Process process;
		try {
			process = pb.start();
			// Read output
			StringBuilder out = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null, previous = null;
			while ((line = br.readLine()) != null)
				if (!line.equals(previous)) {
					previous = line;
					out.append(line).append('\n');
				}

			// Check result
			if (process.waitFor() == 0)
				return out.toString();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Write InputStream to File
	 * 
	 * @param inputStream
	 * @param fileLocation
	 */
	public static void writeToFile(InputStream inputStream, String fileLocation) {
		try {
			OutputStream out = new FileOutputStream(new File(fileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(fileLocation));
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Write byte[] to file
	 * 
	 * @param data
	 * @param fileLocation
	 */
	public static void writeToFile(byte[] data, String fileLocation) {
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(fileLocation);
			stream.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
