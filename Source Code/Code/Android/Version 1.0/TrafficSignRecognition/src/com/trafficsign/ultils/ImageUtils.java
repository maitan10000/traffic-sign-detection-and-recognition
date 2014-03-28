package com.trafficsign.ultils;

import java.util.ArrayList;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.trafficsign.json.ResultInput;




import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtils {
	public static Bitmap drawImage(String filePath, ArrayList<ResultInput> info) {
		Mat image = Highgui.imread(filePath);
		//draw rect
		for(int i = 0; i < info.size(); i++){
			Rect rect = new Rect();
			rect.height = info.get(i).getLocate().getHeight();
			rect.width = info.get(i).getLocate().getWidth();
			rect.y = info.get(i).getLocate().getY();
			rect.x = info.get(i).getLocate().getX();
			Core.rectangle(image, rect.tl(), rect.br(), new Scalar(204, 51, 204), 3);
			Point point = new Point();
			point.x = rect.x;
			point.y = rect.y + 20 ;
			//int fontFace = FONT_HERSHEY_SCRIPT_SIMPLEX;
			//double fontScale = 2;
			Core.putText(image, (i+1) +"", point, 1, 3, new Scalar(204, 51, 204), 3);
		}
		Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2BGRA);
		Bitmap bmp = Bitmap.createBitmap(image.cols(), image.rows(), Bitmap.Config.ARGB_8888);
	    Utils.matToBitmap(image, bmp);
	    return bmp;
		
	}
	// convert image to bitmap
	public static Bitmap convertToBimap(String filePath){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		return BitmapFactory.decodeFile(filePath, options);
	}

}
