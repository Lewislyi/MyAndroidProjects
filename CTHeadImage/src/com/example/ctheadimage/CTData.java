package com.example.ctheadimage;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

public class CTData extends Application{
	private short cthead[][][];
	//private Map<Short, Integer> hashMap;
	//private Map<Short, Float> histogram;
	private short min, max;
	private static CTData instance;
	private CTHeadDataBase dbHelper;
	private CTheadJNI m_ctheadjni = null;
	private CTData(){
		//this.cthead = new short[113][256][256];
		//this.hashMap = new HashMap<Short, Integer>();
		//this.histogram = new HashMap<Short, Float>();
		//this.min =0;
		//this.max =0;
		instance = null;
	}
	public static CTData getInstance(){
		if(instance == null)
			instance = new CTData();
		return instance;
	}
		
	
	public short getCTData(int z, int x, int y){
			return cthead[z][x][y];
	}
	
	//将2bytes CT图像转成 4bytes数据图像然后创建bitmap，第一个byte为0xff，2-4btye数据映射成【0-255】灰度级别的图像数据
    //mode: 1: Front 2: Top 3: Side
	public Bitmap createBitmap(int slice, int mode, int highlight){
		if (m_ctheadjni == null)
			return null;
		int width, height;
		int[] color = new int[512*512];
		switch(mode){
			case 1:
				m_ctheadjni.readFrontData(slice, color, color.length);
				break;
			case 2:
				m_ctheadjni.readTopData(slice, color, color.length);
				break;
			default:
				m_ctheadjni.readSideData(slice, color, color.length);
				break;
		}
    	Bitmap bmp = null; 
        try {  
        	bmp = Bitmap.createBitmap(color, 0, 512, 512, 512,   
                    Bitmap.Config.RGB_565);  
        } catch (Exception e) {  
            // TODO: handle exception  
            return null;  
        }                 
        return bmp;  
		
	}
//	public Bitmap createBitmap(int slice, int mode, int highlight){ 
//		int width, height;
//        Bitmap bmp = null; 
//		if(cthead == null){
//			return bmp;
//		} 
//		switch(mode){
//			case 1:
//				width = 256;
//				height = 113;
//				break;
//			case 2:
//				width = 256;
//				height = 256;
//				break;
//			case 3:
//				width = 256;
//				height = 113;
//				break;
//			default:
//				width = 256;
//				height = 256;
//				break;
//		}
//    	int[] color = new int[width * height];
//    	int nIndex = 0, k = 0;
//    	byte col;
//    	short datum;    	
////    	for(int i = 0; i < height; i++){
////    		for(int j = 0; j < width; j++){
////    			if(nIndex < height * width){
////    				if(mode == 1){
////    					if(highlight != 0){
////	    					k = 0;
////	    					datum = cthead[i][k][j];
////	    					for(k = 0; k < 256; k ++){
////	    						if(cthead[i][k][j] > datum)
////	    							datum = cthead[i][k][j];
////	    					}
////    					}else{
////    						datum = cthead[i][slice][j];
////    					}
////    				}
////    				else if(mode == 2){
////    					if(highlight != 0){
////        					k = 0;
////        					datum = cthead[k][i][j];
////        					for(k = 0; k < 113; k ++){
////        						if(cthead[k][i][j] > datum)
////        							datum = cthead[k][i][j];
////        					}
////    					}else{
////    						datum = cthead[slice][i][j];
////    					}
////    				}
////    				else{
////    					if(highlight != 0){
////        					k = 0;
////        					datum = cthead[i][j][k];
////        					for(k = 0; k < 256; k ++){
////        						if(cthead[i][j][k] > datum)
////        							datum = cthead[i][j][k];
////        					}
////    					}else{
////    						datum = cthead[i][j][slice];
////    					}
////    				}
////    				/*
////    				col=(byte)(255.0f*((float)datum-(float)min)/((float)(max-min)));
////    				color[nIndex] = (col << 16 & 0x00FF0000) |   
////                    (col << 8 & 0x0000FF00 ) |   
////                    (col & 0x000000FF ) |   
////                     0xFF000000;*/
////    				//convertToColor(datum, color, width, height);
////    				//int tmp =  convertToColor(datum);
////    				color[nIndex++] = convertToColor(datum);
////    			}
////    			else
////    				break;
////    		}
////    	}
//    	int newColor[] = convertTo512(color, width, height);
//    	if(highlight == 3)
//    		createHistogram(newColor);
//        try {  
//        	bmp = Bitmap.createBitmap(newColor, 0, 512, 512, 512,   
//                    Bitmap.Config.RGB_565);  
//        } catch (Exception e) {  
//            // TODO: handle exception  
//            return null;  
//        }                 
//        return bmp;  
//    }
		
	public int createCTData(Context context) throws IOException{		
//		int i, j, k;
//		int b1,b2;
//		short read;
//		InputStream in = context.getResources().getAssets().open("CThead");
//		DataInputStream buffer = new DataInputStream(new BufferedInputStream(in));
//		//loop through the data reading it in
//		for (k=0; k<113; k++) {
//			for (j=0; j<256; j++) {
//				for (i=0; i<256; i++) {
//					//because the Endianess is wrong, it needs to be read byte at a time and swapped
//					b1=((int)buffer.readByte()) & 0xff; //the 0xff is because Java does not have unsigned types (C++ is so much easier!)
//					b2=((int)buffer.readByte()) & 0xff; //the 0xff is because Java does not have unsigned types (C++ is so much easier!)
//					read=(short)((b2<<8) | b1); //and swizzle the bytes around
//					if (read<min) min=read; //update the minimum
//					if (read>max) max=read; //update the maximum
//					cthead[k][j][i]=read; //put the short into memory (in C++ you can replace all this code with one fread)
//				}
//			}
//		}
		if(m_ctheadjni == null)
			m_ctheadjni = new CTheadJNI();
		m_ctheadjni.readFromAssets(context.getResources().getAssets(), "CThead");
		return 0;
	}
	
	private int createHistogram(int[] colorData){
		Map<Integer, Integer> hashMap = new HashMap<Integer, Integer>();//用于统计像素点个数
		Map<Integer, Float> percentMap = new HashMap<Integer, Float>();//由于统计积累概率
		float percent;
		//先统计像素点
		for(int i = 0; i < colorData.length; i++){
			if(hashMap.containsKey(colorData[i]) == false)
				hashMap.put(colorData[i], 1);
			else {
				int oldCount = hashMap.get(colorData[i]);
				hashMap.put(colorData[i], ++oldCount);
			}
		}
		for(Map.Entry<Integer, Integer>m : hashMap.entrySet()){
			int m_key = m.getKey();
			//计算像素概率
			percent = (float)hashMap.get(m_key) / (float)colorData.length;
			//Log.d("log","counter " + hashMap.get(m_key) + "percent " + percent);
			for(Map.Entry<Integer, Integer> k : hashMap.entrySet()){
				int k_key = k.getKey();
				if(k_key < m_key){
					//计算积累概率
					percent = percent + ((float)hashMap.get(k_key) / (float)colorData.length);
				}
			}
			percentMap.put(m_key, percent);
			//Log.d("log","color " + m_key + "cumpercent " + percent);
		}
		for(int i = 0; i < colorData.length; i++){
			//利用积累概率映射到【0-255】灰度级别
			short processPixcel = (byte)(percentMap.get(colorData[i]) * 255.0f);
			//再将像素点抓化成4字节数据
			colorData[i] = (processPixcel << 16 & 0x00FF0000) |   
			        (processPixcel << 8 & 0x0000FF00 ) |   
			        (processPixcel & 0x000000FF ) |   
			         0xFF000000;
		}
		return 0;
	}
	
			
	private int convertToColor(short pixcel){
		short pixceldata =(byte)(255.0f*((float)pixcel-(float)min)/((float)(max-min)));
		int pixcelcolor= (pixceldata << 16 & 0x00FF0000) |   
        (pixceldata << 8 & 0x0000FF00 ) |   
        (pixceldata & 0x000000FF ) |   
         0xFF000000;
		return pixcelcolor;
	}

	private int[] convertTo512(int[] color, int width, int height){
		int[] newColor = new int[512 * 512];
		for(int i = 0; i < 512; i++){
			for(int j = 0; j < 512; j++){
				int oldI = (int)(((float)i/511) * (height - 1));
				int oldJ = (int)(((float)j/511) * (width -1));
				int oldPos = (width * oldI) + oldJ;
				int newPos = (i*512) + j;
				if(newPos < newColor.length){
					//Log.d("convertTo512", "pos "+ oldJ + "," + oldI + " value " + color[oldPos]);
					newColor[newPos] = color[oldPos];
				}
			}
		}
 		return newColor;
	}

}
