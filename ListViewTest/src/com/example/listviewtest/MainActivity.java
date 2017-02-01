package com.example.listviewtest;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.*;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listviewtest.FruitAdapter;

public class MainActivity extends Activity {

	
	//private String[] data = {"Apple", "Banana", "Orange", "Watermelon", 
			//"Pear", "Grape", "Pinapple", "Stawberry", "Cherry", "Mango"};
	private List<Fruit> fruitlist = new ArrayList<Fruit>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);
		initFruit();//初始化水果
		FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.fruit_item, fruitlist);
		ListView listview = (ListView) findViewById(R.id.list_view);
		listview.setAdapter(adapter);
		//设置按钮监听事件
		listview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				Fruit fruit = fruitlist.get(position);
				Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void initFruit()
	{
		Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
		fruitlist.add(apple);
		Fruit banans = new Fruit("Banans", R.drawable.apple_pic);
		fruitlist.add(banans);
		Fruit orange = new Fruit("Orange", R.drawable.apple_pic);
		fruitlist.add(orange);
		Fruit watermelon = new Fruit("Watermelon", R.drawable.apple_pic);
		fruitlist.add(watermelon);
		Fruit pear = new Fruit("Pear", R.drawable.apple_pic);
		fruitlist.add(pear);
		Fruit grape = new Fruit("Grape", R.drawable.apple_pic);
		fruitlist.add(grape);
		Fruit pinapple = new Fruit("Pinapple", R.drawable.apple_pic);
		fruitlist.add(pinapple);
		Fruit strawberry = new Fruit("Strawberry", R.drawable.apple_pic);
		fruitlist.add(strawberry);
		Fruit cherry = new Fruit("Cherry", R.drawable.apple_pic);
		fruitlist.add(cherry);	
		Fruit mango = new Fruit("Mango", R.drawable.apple_pic);
		fruitlist.add(mango);
		Fruit kiwifruit = new Fruit("Kiwifruit", R.drawable.apple_pic);
		fruitlist.add(kiwifruit);
		Fruit coconut = new Fruit("Coconut", R.drawable.apple_pic);
		fruitlist.add(coconut);
		Fruit DragonFruit = new Fruit("Dragon Fruit", R.drawable.apple_pic);
		fruitlist.add(DragonFruit);
	}

}
