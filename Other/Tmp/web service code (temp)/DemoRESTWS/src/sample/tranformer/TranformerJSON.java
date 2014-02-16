package sample.tranformer;
import java.util.ArrayList;

import com.google.gson.Gson;

import sample.dto.CategoryDTO;

public class TranformerJSON {
	public static String Cate(ArrayList<CategoryDTO> result)
	{
	String result1 = null;
	Gson gson = new Gson();
	result1 = gson.toJson(result);
	return result1;
	}
}
