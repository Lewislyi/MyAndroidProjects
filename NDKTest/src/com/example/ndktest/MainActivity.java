package com.example.ndktest;

import com.apress.swig.Unix;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	hellojni m_jni = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView textview = (TextView) findViewById(R.id.ndkstring);
		//tv.setText(new hellojni().NDKTestFromJNI());
		//textview.setText("UID:" + new Unix().getuid() + " " + new hellojni().NDKTestFromJNI());
		m_jni = new hellojni();
		m_jni.readFromAssets(getAssets(),"CThead");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	static{
		System.loadLibrary("NDKTest");
		
	}
}
