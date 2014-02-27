package com.example.ultils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vo.ResultInput;

public class ConvertUtil {

	/**
	 * Converting objects to byte arrays
	 */
	static public byte[] object2Bytes(Object o) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		return baos.toByteArray();
	}

	/**
	 * Converting byte arrays to objects
	 */
	static public Object bytes2Object(byte raw[]) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(raw);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object o = ois.readObject();
		return o;
	}
	/*Parse jsonArray to list Result Input*/
	public static ArrayList<ResultInput> parseToList(JSONArray jsonArray){
		ArrayList<ResultInput> output = new ArrayList<ResultInput>();
		for(int i = 0; i < jsonArray.length(); i++){
			ResultInput temp = new ResultInput();
			JSONObject jsonTemp = new JSONObject();
			try {
				 jsonTemp = jsonArray.getJSONObject(i).getJSONObject("Locate");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				temp.setTrafficID(jsonArray.getJSONObject(i).getString("ID"));
				temp.setHeight(jsonTemp.getInt("height"));
				temp.setWidth(jsonTemp.getInt("width"));
				temp.setX(jsonTemp.getInt("x"));
				temp.setY(jsonTemp.getInt("y"));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			output.add(temp);
		}
		return output;
	}
	
}
