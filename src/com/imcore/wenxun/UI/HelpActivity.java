package com.imcore.wenxun.UI;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;

public class HelpActivity extends FragmentActivity{
	
	private ViewPager mViewPager;
	private List<ImageView> pointlist;
	private ImageView mImageView1,mImageView2,mImageView3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_activity);
		
		mImageView1=(ImageView) findViewById(R.id.yes_point);
		mImageView2=(ImageView) findViewById(R.id.no_point1);
		mImageView3=(ImageView) findViewById(R.id.no_point2);
		pointlist=new ArrayList<ImageView>();
		pointlist.add(mImageView1);
		pointlist.add(mImageView2);
		pointlist.add(mImageView3);
		
		mViewPager=(ViewPager) findViewById(R.id.supportviewpager);
		mViewPager.setAdapter(new viewpagerAdapter(getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(onpage);
	}

	
	
	class viewpagerAdapter extends FragmentStatePagerAdapter {

		public viewpagerAdapter(FragmentManager fm) {
			super(fm);
		}
        
		@Override
		public int getCount() {
			return pointlist.size();
		}
		
		
		@Override
		public Fragment getItem(int position) {
			HelpFragment hf=new HelpFragment();
			Bundle bn=new Bundle();
			bn.putInt("point", position);
			hf.setArguments(bn);
			return hf;
		}
	
	}
	
	public OnPageChangeListener onpage=new OnPageChangeListener(){

		@Override
		public void onPageScrollStateChanged(int position) {
			
		}

		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int position) {
			for(int i=0;i<pointlist.size();i++){
				if(i==position){
					pointlist.get(position).setImageResource(R.drawable.yes);
				}else{
					pointlist.get(i).setImageResource(R.drawable.no);
				}
			}
		}
		
		
	};
}
