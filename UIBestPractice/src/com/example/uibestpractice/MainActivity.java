package com.example.uibestpractice;

import java.util.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;

public class MainActivity extends Activity {
	private ListView msgListView;
	private EditText inputText;
	private Button send;
	private MsgAdapter adapter;
	private List<Msg> msgList = new ArrayList<Msg>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initMsgs();
		adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item, msgList);
		inputText = (EditText) findViewById(R.id.input_text);
		send = (Button) findViewById(R.id.send);
		msgListView = (ListView) findViewById(R.id.msg_list_view);
		msgListView.setAdapter(adapter);
		send.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				String content = inputText.getText().toString();
				if(!"".equals(content)){
					Msg msg = new Msg(content, Msg.TYPE_SENT);
					msgList.add(msg);
					//有消息时，刷新listview显示
					adapter.notifyDataSetChanged();
					//将listview定位到最后一行
					msgListView.setSelection(msgList.size());
					//清空输入框
					inputText.setText("");
				}
			}
		});
	}
	
	private void initMsgs(){
		Msg msg1 = new Msg("Hello", Msg.TYPE_RECEIVED);
		msgList.add(msg1);
		Msg msg2 = new Msg("How are you ?", Msg.TYPE_SENT);
		msgList.add(msg2);
		Msg msg3 = new Msg("Good, Let's go out for fun", Msg.TYPE_RECEIVED);
		msgList.add(msg3);
		Msg msg4 = new Msg("Sure", Msg.TYPE_SENT);
		msgList.add(msg4);
	}
}
