package sample.tranformer;

import java.util.ArrayList;

import com.google.gson.Gson;

import sample.dto.TrafficInfoDTO;

public class TrafficInfoTrans {
 public static String Traffic(ArrayList<TrafficInfoDTO> result){
	 String result1 = null;
		Gson gson = new Gson();
		result1 = gson.toJson(result);
		return result1;
 }
}
