package com.imcore.wenxun.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HelpFragment extends Fragment{
	
	private static final int[] image={R.drawable.welcompage1,R.drawable.welcompage2,R.drawable.welcompage3};
	private ImageView mImageView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.helpfragment_activity,null);
		
		Bundle bn=getArguments();
		int position=bn.getInt("point");
		mImageView=(ImageView)view.findViewById(R.id.helpfragment_imageView);
		mImageView.setImageResource(image[position]);
		
		return view;

  }

}