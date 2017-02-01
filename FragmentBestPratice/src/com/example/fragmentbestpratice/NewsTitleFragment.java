package com.example.fragmentbestpratice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NewsTitleFragment extends Fragment implements OnItemClickListener{
	private ListView newsTitleListView;
	private List<News> newsList;
	private NewsAdapter adapter;
	private boolean isTwoPane;
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		newsList = getNews();
		//对于listview的列表 我们需要传入每个item的布局，和整个列表的对象
		adapter = new NewsAdapter(activity, R.layout.new_item, newsList);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.news_title_frag, container, false);
		//这里设置listview的相关参数
		newsTitleListView = (ListView) view.findViewById(R.id.news_title_list_view);
		newsTitleListView.setAdapter(adapter);
		newsTitleListView.setOnItemClickListener(this);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		/*
		if(getActivity().findViewById(R.id.news_content_layout) != null){
			isTwoPane = true;
		}
		else{
			isTwoPane = false;
		}*/
		isTwoPane = false;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int postion, long id){
		News news = newsList.get(postion);
		//如果是双页模式,则刷新NewContentFragment中的内容
		if(isTwoPane){
			//NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
			//newsContentFragment.refresh(news.getTitle(), news.getContent());
		}
		else{
			//如果是单页模式就直接启动NewsContentActivity(碎片和活动进行关联)
			NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
		}
	}
	
	private List<News> getNews(){
		List<News> newsList = new ArrayList<News>();
		News news1 = new News();
		news1.setTitle("Succeed in College as a learning Disabled Students");
		news1.setContent("6666666666666");
		newsList.add(news1);
		News news2 = new News();
		news2.setTitle("Chelsea beated arsenal with 2:0");
		news2.setContent("Today Chelsea beated arsenal with 2:0 in standford bridge");
		newsList.add(news2);
		return newsList;
	}
}
