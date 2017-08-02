package com.example.ctheadimage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.widget.Toast;
import android.database.sqlite.SQLiteOpenHelper;

public class CTHeadDataBase extends SQLiteOpenHelper{
		public static final String CREATE_CTDATA = "create table CTData ("
				+ "id integer primary key autoincrement," 
				+ "ctdata blob)";
		
		private Context mContext;
		
		public CTHeadDataBase(Context context, String name, CursorFactory factory, int version){
			super(context, name, factory, version);
			mContext = context;
		}
		
		@Override
		public void onCreate(SQLiteDatabase db){
			db.execSQL(CREATE_CTDATA);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
			
		}
		
}
