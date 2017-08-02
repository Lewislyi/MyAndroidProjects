package com.example.ctheadimage;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class TopView extends Fragment{
	private SeekBar seekbar;
	private ImageView imgview;
	private Button highlight;
	private Button histogram;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view= inflater.inflate(R.layout.fragment_top, container, false);
		seekbar = (SeekBar)view.findViewById(R.id.top_slider);
		imgview = (ImageView)view.findViewById(R.id.top_img);
		highlight = (Button)view.findViewById(R.id.Top_highLight);
		histogram = (Button)view.findViewById(R.id.Top_HistogramEQ);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				Bitmap curimg = null;
				curimg = CTData.getInstance().createBitmap(progress, 2, 0);
				imgview.setImageBitmap(curimg);
			}
		});
		highlight.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Bitmap highlight_img = CTData.getInstance().createBitmap(0, 2, 1);
				imgview.setImageBitmap(highlight_img);
			}
		});
		histogram.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Bitmap histogram_img = CTData.getInstance().createBitmap(0, 2, 3);
				imgview.setImageBitmap(histogram_img);
			}
		});
		Bitmap img = null;
		img = CTData.getInstance().createBitmap(seekbar.getProgress(), 2, 0);
		imgview.setImageBitmap(img);
		return view;
	}
}
