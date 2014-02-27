package com.example.ultils;

import java.util.ArrayList;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;

import vo.ResultInput;

import android.graphics.Bitmap;

public class ImageUtils {
	public static Bitmap drawImage(String filePath, ArrayList<ResultInput> info) {
		Mat image = Highgui.imread(filePath);
		//draw rect
		for(int i = 0; i < info.size(); i++){
			Rect rect = new Rect();
			rect.height = info.get(i).getHeight();
			rect.width = info.get(i).getWidth();
			rect.y = info.get(i).getY();
			rect.x = info.get(i).getX();
			Core.rectangle(image, rect.tl(), rect.br(), new Scalar(204, 51, 204), 10);
			Point point = new Point();
			point.x = rect.x + 10;
			point.y = rect.y + 120;
			//int fontFace = FONT_HERSHEY_SCRIPT_SIMPLEX;
			//double fontScale = 2;
			Core.putText(image, (i+1) +"", point, 1, 10, new Scalar(204, 51, 204), 20);
		}
		Bitmap bmp = Bitmap.createBitmap(image.cols(), image.rows(), Bitmap.Config.ARGB_8888);
	    Utils.matToBitmap(image, bmp);
	    return bmp;
		
	}
}
