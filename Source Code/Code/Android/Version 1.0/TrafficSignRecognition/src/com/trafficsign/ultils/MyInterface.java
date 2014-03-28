package com.trafficsign.ultils;

import java.util.EventListener;

import org.json.JSONArray;

import android.graphics.Bitmap;

public class MyInterface {
	public interface IAsyncHttpListener extends EventListener {
	    void onComplete(String respond);
	}
	public interface IAsyncHttpImageListener extends EventListener {
	    void onComplete(Bitmap bitmap, int extra);
	}
}