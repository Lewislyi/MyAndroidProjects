package com.example.ctheadimage;

import android.content.res.AssetManager;

public class CTheadJNI {
	public native void readFromAssets(AssetManager ass,String filename);
	public native void readSideData(int slide, int[] data, int size);
	public native void readTopData(int slide, int[] data, int size);
	public native void readFrontData(int slide, int[] data, int size);
}
