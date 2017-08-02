package com.example.ctheadimage;

import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;

public class CTHeadService extends Service{
	
	class MyThread implements Runnable{
		private Context mContext;
		MyThread(Context context){
			this.mContext = context;
		}
		
		@Override
		public void run(){
				//初始化CT数据
				CTData dataset;
				dataset = CTData.getInstance();
				try {
					dataset.createCTData(mContext);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	@Override 
	public IBinder onBind(Intent intent){
		return null;
	}
	
	@Override
	//创建CTHead数据库
	public void onCreate(){
		super.onCreate();	
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		MyThread mTd = new MyThread(this);
		new Thread(mTd).start();
//		byte[] ctdata = null;
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		Cursor cursor = db.query("CTDataBase.db", null, null, null, null, null, null);
//		if(cursor.moveToFirst()){
//			ctdata = cursor.getBlob(cursor.getColumnIndex("ctdata"));
//		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	
}
