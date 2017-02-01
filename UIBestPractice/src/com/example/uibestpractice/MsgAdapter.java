package com.example.uibestpractice;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MsgAdapter extends ArrayAdapter<Msg> {
	private int resourceId;
	public MsgAdapter(Context context, int textViewResourceId, List<Msg> objects){
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		Msg msg = getItem(position);
		View view;
		ViewHolder viewholder;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewholder = new ViewHolder();
			viewholder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
			viewholder.leftLayout= (LinearLayout) view.findViewById(R.id.left_layout);
			viewholder.leftMsg = (TextView) view.findViewById(R.id.left_msg);
			viewholder.rightMsg = (TextView) view.findViewById(R.id.right_msg);
			view.setTag(viewholder);
		}
		else{
			view = convertView;
			viewholder = (ViewHolder) view.getTag();
		}
		if(msg.getType() == Msg.TYPE_RECEIVED){
			//如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
			viewholder.leftLayout.setVisibility(view.VISIBLE);
			//隐藏布局
			viewholder.rightLayout.setVisibility(View.GONE);
			viewholder.leftMsg.setText(msg.getContent());
		}else if(msg.getType() == Msg.TYPE_SENT){
			//如果是发送的消息，则显示右边的消息布局，将左边的消息布局隐藏
			viewholder.rightLayout.setVisibility(view.VISIBLE);
			//隐藏布局
			viewholder.leftLayout.setVisibility(View.GONE);
			viewholder.rightMsg.setText(msg.getContent());
		}
		return view;
	}
	
	class ViewHolder{
		LinearLayout leftLayout;
		LinearLayout rightLayout;
		TextView leftMsg;
		TextView rightMsg;
	}
}
