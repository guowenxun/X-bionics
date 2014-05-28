package com.imcore.wenxun.UI;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FirstPagerActivity extends Activity{
	
	private DrawerLayout mDrawerLayout;
	private Button mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstpager_activity);
		
		mDrawerLayout=(DrawerLayout) findViewById(R.id.drawerlayoutId);
		
		mButton=(Button) findViewById(R.id.drawerlayout_button);
		mButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mDrawerLayout.openDrawer(Gravity.LEFT);
			}});
	}
}
