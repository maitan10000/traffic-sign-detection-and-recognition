package com.trafficsign.ultils;

import com.trafficsign.json.ResultShortJSON;

public class Helper {
	public static boolean isEmail(String email) {
		if (email == null) {
			return false;
		} else {
			return email.contains(" ") == false
					&& email.matches(".+@.+\\.[a-z]+");
		}

	}
}
