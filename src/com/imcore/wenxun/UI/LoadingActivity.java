package com.imcore.wenxun.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;


public class LoadingActivity extends Activity{

	private ProgressBar mProgressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_activity);
		init();
		
	}
    private void init(){
    	mProgressBar=(ProgressBar) findViewById(R.id.progressbarid);
    	new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
					if(mProgressBar.getProgress()>=100){
						Intent intent=new Intent(LoadingActivity.this,LoginMainActivity.class);
						startActivity(intent);
						finish();
						return;
					}
					try {
						Thread.sleep(500);
						mProgressBar.incrementProgressBy(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}}).start();
    }
	
}
