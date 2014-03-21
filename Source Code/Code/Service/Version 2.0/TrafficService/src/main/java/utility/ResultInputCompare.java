package utility;

import java.util.Comparator;

import json.ResultInput;

//compare ResultInput
public class ResultInputCompare implements Comparator<ResultInput> {
	public int compare(ResultInput arg0, ResultInput arg1) {
		if (arg0.getLocate().getY() > arg1.getLocate().getY()) {
			return 1;
		}
		if (arg0.getLocate().getY() < arg1.getLocate().getY()) {
			return -1;
		}
		if (arg0.getLocate().getX() > arg1.getLocate().getX()) {
			return 1;
		}
		if (arg0.getLocate().getX() < arg1.getLocate().getX()) {
			return -1;
		}
		return 0;
	}
}