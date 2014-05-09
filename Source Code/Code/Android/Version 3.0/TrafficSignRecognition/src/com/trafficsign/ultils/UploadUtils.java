package com.trafficsign.ultils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.trafficsign.ultils.MyInterface.IUploadProgressListener;

import android.app.ProgressDialog;
import android.util.Log;

public class UploadUtils {	
	private IUploadProgressListener uploadListener = null;
		
	public void setUploadListener(IUploadProgressListener uploadListener) {
		this.uploadListener = uploadListener;
	}

	public static String uploadFile(String sourceFileUri,
			String upLoadServerUri, ArrayList<NameValuePair> parameters) {
		int serverResponseCode = 0;
		String reponseJson = "";

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);

		if (!sourceFile.isFile()) {
			return reponseJson;
		} else {
			try {
				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				URL url = new URL(upLoadServerUri);

				// Open a HTTP connection to the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("file", sourceFile.getName());

				dos = new DataOutputStream(conn.getOutputStream());
				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""
						+ sourceFile.getName() + "\"" + lineEnd);
				dos.writeBytes(lineEnd);

				// create a buffer of maximum size
				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);				
				for (NameValuePair nameValuePair : parameters) {
					dos.writeBytes(twoHyphens + boundary + lineEnd);
					dos.writeBytes("Content-Disposition: form-data; name=\""
							+ nameValuePair.getName() + "\"" + lineEnd);
					dos.writeBytes(lineEnd);
					dos.writeBytes(nameValuePair.getValue());
					dos.writeBytes(lineEnd);
				}
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				Log.i("uploadFile", "HTTP Response ne2 : " + serverResponseCode);
				
				if (serverResponseCode == 200) {

					InputStream in = conn.getInputStream();
					InputStreamReader isr = new InputStreamReader(in);
					int charRead;
					int BUFFER_SIZE = 2000;
					char[] inputBuffer = new char[BUFFER_SIZE];
					try {
						while ((charRead = isr.read(inputBuffer)) > 0) {
							// ---convert the chars to a String---
							String readString = String.copyValueOf(inputBuffer,
									0, charRead);
							reponseJson += readString;
							inputBuffer = new char[BUFFER_SIZE];
						}
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Log.i("uploadFile", "HTTP Response ne3 : " + reponseJson);					
				} else if (serverResponseCode != 200) {
					reponseJson = "Error: " + serverResponseCode;
				}

				// close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {
				ex.printStackTrace();				
				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("Upload file to server Exception",
						"Exception : " + e.getMessage(), e);
			}
			return reponseJson;

		} // End else block
	}
	
	public String uploadFileWithProgress(String sourceFileUri,
			String upLoadServerUri, ArrayList<NameValuePair> parameters) {
		int serverResponseCode = 0;
		String reponseJson = "";

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1024 * 10;
		File sourceFile = new File(sourceFileUri);

		if (!sourceFile.isFile()) {
			return reponseJson;
		} else {
			try {
				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				URL url = new URL(upLoadServerUri);

				// Open a HTTP connection to the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("file", sourceFile.getName());

				dos = new DataOutputStream(conn.getOutputStream());
				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""
						+ sourceFile.getName() + "\"" + lineEnd);
				dos.writeBytes(lineEnd);

				// create a buffer of maximum size
				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				long fileSize = sourceFile.length();
				long uploadedSize = 0;
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				while (bytesRead > 0) {
					dos.write(buffer, 0, bufferSize);
					if(this.uploadListener != null)
					{
						uploadedSize += bytesRead;
						int percent = (int)(uploadedSize*100/fileSize);
						this.uploadListener.onProgress(percent);
					}
					
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);					
				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);				
				for (NameValuePair nameValuePair : parameters) {
					dos.writeBytes(twoHyphens + boundary + lineEnd);
					dos.writeBytes("Content-Disposition: form-data; name=\""
							+ nameValuePair.getName() + "\"" + lineEnd);
					dos.writeBytes(lineEnd);
					dos.writeBytes(nameValuePair.getValue());
					dos.writeBytes(lineEnd);
				}
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				Log.i("uploadFile", "HTTP Response ne2 : " + serverResponseCode);
				
				if (serverResponseCode == 200) {

					InputStream in = conn.getInputStream();
					InputStreamReader isr = new InputStreamReader(in);
					int charRead;
					int BUFFER_SIZE = 2000;
					char[] inputBuffer = new char[BUFFER_SIZE];
					try {
						while ((charRead = isr.read(inputBuffer)) > 0) {
							// ---convert the chars to a String---
							String readString = String.copyValueOf(inputBuffer,
									0, charRead);
							reponseJson += readString;
							inputBuffer = new char[BUFFER_SIZE];
						}
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Log.i("uploadFile", "HTTP Response ne3 : " + reponseJson);					
				} else if (serverResponseCode != 200) {
					reponseJson = "Error: " + serverResponseCode;
				}

				// close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {
				ex.printStackTrace();				
				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("Upload file to server Exception",
						"Exception : " + e.getMessage(), e);
			}
			
			if(this.uploadListener != null)
			{
				this.uploadListener.onComplete();
			}
			return reponseJson;

		} // End else block
	}
}
