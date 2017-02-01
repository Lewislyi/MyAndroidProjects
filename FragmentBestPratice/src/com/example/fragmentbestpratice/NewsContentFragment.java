package com.example.fragmentbestpratice;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsContentFragment extends Fragment {
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		view = inflater.inflate(R.layout.news_content_frag, container, false);
		return view;
	}
	//用于将内容和标题显示在控件上
	public void refresh(String newsTitle, String newsContent){
		View visibilityLayout = view.findViewById(R.id.visibility_layout);
		visibilityLayout.setVisibility(View.VISIBLE);
		//获取标题控件
		TextView newsTitleText = (TextView)view.findViewById(R.id.news_title);
		//获取内容控件
		TextView newsContentText = (TextView)view.findViewById(R.id.news_content);
		newsTitleText.setText(newsTitle);
		newsContentText.setText(newsContent);
	}
}
