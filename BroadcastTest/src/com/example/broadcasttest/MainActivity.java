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
		//��ȡ���ع㲥�Ŀ�����ʵ��
		localBroadcastManager = LocalBroadcastManager.getInstance(this);
		
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				//Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
				Intent intent = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
				//���ͱ�׼ȫ�ֹ㲥
				//sendBroadcast(intent);
				//������㲥
				//sendOrderedBroadcast(intent, null);
				//���ͱ���Ӧ�ù㲥
				localBroadcastManager.sendBroadcast(intent);
			}	
		});
		intentFilter = new IntentFilter();
		//ÿ������״̬�ı�ʱ��ϵͳ�ᷢ�������㲥
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		//���뱾�ع㲥��Ϣ
		intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
		networkChangeReceiver = new NetworkChangeReceiver();
		//��ʵ��ע�ᵽ��������
		registerReceiver(networkChangeReceiver, intentFilter);
		
		localReceiver = new LocalReceiver();
		//ʹ��localBroadManager ���б��ع㲥��ע��
		localBroadcastManager.registerReceiver(localReceiver, intentFilter);
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		//��������ٵ�ʱ��ע���㲥
		unregisterReceiver(networkChangeReceiver);
		localBroadcastManager.unregisterReceiver(localReceiver);
	}
	//�ڲ��࣬������״̬�ı䣬�ͻ�õ�ִ��
	class NetworkChangeReceiver extends BroadcastReceiver{
		
		@Override
		public void onReceive(Context context, Intent intent){
			//��ȡϵͳ�ؼ�����ʱ����Ҫ��ȡϵͳȨ�޷������н������쳣����AndroidMainfest������Ȩ��
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

