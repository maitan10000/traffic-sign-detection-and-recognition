package utility;

import java.lang.reflect.Type;
import java.text.DateFormat;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

	public static <T>T fromJson(String jsonString, Type typeOfT) {
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG, DateFormat.LONG).create();
		return gson.fromJson(jsonString, typeOfT);
	}

	public static String toJson(Object object) {
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.LONG, DateFormat.LONG).create();
		return gson.toJson(object);
	}

}
