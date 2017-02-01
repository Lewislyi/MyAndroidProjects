package com.example.listviewtest;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FruitAdapter extends ArrayAdapter<Fruit>{
	private int resourceId;
	public FruitAdapter(Context context, int textViewResourceId, List<Fruit> objects){
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View converView, ViewGroup parent){
		Fruit fruit = getItem(position);
		View view;
		ViewHolder holder;
		//之前已经加载过的布局就直接用缓存，没加载过得在从布局中获取控件
		if(converView == null){
			//加载传入的布局，获取布局实例view
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			//将布局里的控件保存到view holder再保存到布局的tag里
			holder = new ViewHolder(); 
			holder.fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
			holder.fruitName = (TextView) view.findViewById(R.id.fruit_name);
			view.setTag(holder);
		}else{
			view = converView;
			//从布局的标签中取回缓存的控件
			holder = (ViewHolder) view.getTag();
		}
		//重新设置布局中的这个控件的属性
		//ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
		//TextView fruitName = (TextView) view.findViewById(R.id.fruit_name);
		//fruitImage.setImageResource(fruit.getImageId());
		//fruitName.setText(fruit.getName());
		holder.fruitImage.setImageResource(fruit.getImageId());
		holder.fruitName.setText(fruit.getName());
		return view;
	}
	
	class ViewHolder{
		ImageView fruitImage;
		TextView fruitName;
	}
}

