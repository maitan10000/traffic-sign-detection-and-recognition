package com.trafficsign.ultils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.trafficsign.json.ResultShortJSON;

public class Helper {
	// check email
	public static boolean isEmail(String email) {
		if (email == null) {
			return false;
		} else {
			return email.contains(" ") == false
					&& email.matches(".+@.+\\.[a-z]+");
		}

	}

	// coppy file
	public static void copyFileUsingStream(String sourcePath, String destPath) {
		File source = new File(sourcePath);
		File dest = new File(destPath);
		InputStream is = null;
		OutputStream os = null;
		try {
			try {
				is = new FileInputStream(source);
				os = new FileOutputStream(dest);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
