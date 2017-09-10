package com.example.ctheadimage;

import android.content.res.AssetManager;

public class CTheadJNI {
	public native void readFromAssets(AssetManager ass,String filename);
	public native void readSideData(int slide, int[] data, int size);
	public native void readTopData(int slide, int[] data, int size);
	public native void readFrontData(int slide, int[] data, int size);
	public native void readSideHL(int[] arrSideHL, int size);
	public native void readTopHL(int[] arrTopHL, int size);
	public native void readFrontHL(int[] arrFrontHL, int size);
	public native void readHistogram(int[] arrCTData, int size);
}
