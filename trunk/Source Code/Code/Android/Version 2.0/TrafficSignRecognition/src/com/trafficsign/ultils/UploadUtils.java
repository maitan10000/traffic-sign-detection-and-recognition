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

import android.app.ProgressDialog;
import android.util.Log;

public class UploadUtils {
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
				String serverResponseMessage = "ok";

				Log.i("uploadFile", "HTTP Response ne2 : " + serverResponseCode);
				// Toast.makeText(getApplicationContext(),
				// "File Upload Complete: "+
				// serverResponseMessage,Toast.LENGTH_LONG).show();

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
					// runOnUiThread(new Runnable() {
					// public void run() {
					//
					// String msg =
					// "File Upload Completed.\n\n See uploaded file here : \n\n"
					// +" http://www.androidexample.com/media/uploads/"
					// +uploadFileName;
					//
					// messageText.setText(msg);
					// Toast.makeText(UploadToServer.this,
					// "File Upload Complete.",
					// Toast.LENGTH_SHORT).show();
					// }
					// });
				} else if (serverResponseCode != 200) {
					// Toast.makeText(UploadToServer.this, text, duration)
					reponseJson = "Error: " + serverResponseCode;
				}

				// close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {

				ex.printStackTrace();

				// runOnUiThread(new Runnable() {
				// public void run() {
				// messageText.setText("MalformedURLException Exception : check script url.");
				// Toast.makeText(UploadToServer.this, "MalformedURLException",
				// Toast.LENGTH_SHORT).show();
				// }
				// });

				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

				e.printStackTrace();

				// runOnUiThread(new Runnable() {
				// public void run() {
				// messageText.setText("Got Exception : see logcat ");
				// Toast.makeText(UploadToServer.this,
				// "Got Exception : see logcat ",
				// Toast.LENGTH_SHORT).show();
				// }
				// });
				Log.e("Upload file to server Exception",
						"Exception : " + e.getMessage(), e);
			}
			// dialog.dismiss();
			return reponseJson;

		} // End else block
	}
}
