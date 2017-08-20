package com.example.ctheadimage;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

 public class MainActivity extends Activity {
	private TabHost tabhost;
	private RadioGroup radiogroup;
	CTheadJNI m_ctheadjni = null;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		m_ctheadjni = new CTheadJNI();
		m_ctheadjni.readFromAssets(getAssets(), "CThead");
		radiogroup = (RadioGroup)findViewById(R.id.radiogroup);
		tabhost = (TabHost)findViewById(android.R.id.tabhost); 
		tabhost.setup();
        tabhost.addTab(tabhost.newTabSpec("Home").setIndicator("Home")
                .setContent(R.id.fragment_home));
        tabhost.addTab(tabhost.newTabSpec("Front").setIndicator("Front")
                .setContent(R.id.fragment_front));
        tabhost.addTab(tabhost.newTabSpec("Top").setIndicator("Top")
                .setContent(R.id.fragment_top));
        tabhost.addTab(tabhost.newTabSpec("Side").setIndicator("Side")
                .setContent(R.id.fragment_side));
        
        radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("CTheader","checkedId  " + checkedId +"  " + R.id.fragment_front);
                switch (checkedId) {
                case R.id.HomeTab:
                Log.d("CTheader","fragment_home");
                    tabhost.setCurrentTabByTag("Home");
                    //如果需要动画效果就使用
                    //setCurrentTabWithAnim(currentTab, 0, "main");
                    //getSupportActionBar().setTitle("首页");
                    break;
                case R.id.FrontTab:
                	Log.d("CTheader","fragment_front");
                    tabhost.setCurrentTabByTag("Front");
                    //setCurrentTabWithAnim(currentTab, 1, "mycenter");
                    //getSupportActionBar().setTitle("个人中心");
                    break;
                case R.id.TopTab:
                	Log.d("CTheader","fragment_top");
                    tabhost.setCurrentTabByTag("Top");
                    //getSupportActionBar().setTitle("搜索");
                    break;
                case R.id.SideTab:
                	Log.d("CTheader","fragment_side");
                    tabhost.setCurrentTabByTag("Side");
                    //getSupportActionBar().setTitle("搜索");
                    break;
                }
                // 刷新actionbar的menu
                //getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
            }
        });
        Intent startIntent = new Intent(this, CTHeadService.class);
        startService(startIntent);
	}
	
	@Override
	protected void onDestroy(){
		Intent stopIntent = new Intent(this, CTHeadService.class);
		stopService(stopIntent);
		super.onDestroy();
	}
	static{
		System.loadLibrary("CTHeadImage");
	}
	
}
