package com.example.ndktest;

import android.content.res.AssetManager;

public class hellojni {
	public native String NDKTestFromJNI();
	public native void readFromAssets(AssetManager ass,String filename);  
}
