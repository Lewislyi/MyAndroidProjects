package com.example.broadcasttest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.*;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private IntentFilter intentFilter;
	private LocalReceiver localReceiver;
	private LocalBroadcastManager localBroadcastManager;
	private NetworkChangeReceiver networkChangeReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//获取本地广播的控制器实例
		localBroadcastManager = LocalBroadcastManager.getInstance(this);
		
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				//Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
				Intent intent = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
				//发送标准全局广播
				//sendBroadcast(intent);
				//发有序广播
				//sendOrderedBroadcast(intent, null);
				//发送本地应用广播
				localBroadcastManager.sendBroadcast(intent);
			}	
		});
		intentFilter = new IntentFilter();
		//每当网络状态改变时，系统会发送这条广播
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		//加入本地广播消息
		intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
		networkChangeReceiver = new NetworkChangeReceiver();
		//讲实例注册到接收器里
		registerReceiver(networkChangeReceiver, intentFilter);
		
		localReceiver = new LocalReceiver();
		//使用localBroadManager 进行本地广播的注册
		localBroadcastManager.registerReceiver(localReceiver, intentFilter);
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		//当活动被销毁的时候注销广播
		unregisterReceiver(networkChangeReceiver);
		localBroadcastManager.unregisterReceiver(localReceiver);
	}
	//内部类，当网络状态改变，就会得到执行
	class NetworkChangeReceiver extends BroadcastReceiver{
		
		@Override
		public void onReceive(Context context, Intent intent){
			//获取系统关键服务时，需要获取系统权限否则运行将出现异常，在AndroidMainfest中声明权限
			ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
			if(networkInfo != null && networkInfo.isAvailable()){
				Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
			}
			
			//Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
		}
	}
	
	class LocalReceiver extends BroadcastReceiver{
		
		@Override
		public void onReceive(Context context, Intent intent){
			Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show();
		}
	}
}

