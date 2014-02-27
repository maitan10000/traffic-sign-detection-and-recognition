package com.example.ultils;

import java.util.EventListener;

import org.json.JSONArray;

public class MyInterface {
	public interface IAsyncFetchListener extends EventListener {
	    void onComplete(JSONArray obj);
	    void onError(String errorMessage);
	}
}