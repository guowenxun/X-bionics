package com.imcore.wenxun.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RegisterMainActivity extends Activity implements OnClickListener{

	private Button tengxunButton,sinaButton,phoneButton,backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newuserregister_activity);
		
		tengxunButton=(Button) findViewById(R.id.tengxun_register_button);
		sinaButton=(Button) findViewById(R.id.sina_register_button);
		phoneButton=(Button) findViewById(R.id.phone_register_button);
		backButton=(Button) findViewById(R.id.back_register_button);
		
		tengxunButton.setOnClickListener(this);
		sinaButton.setOnClickListener(this);
		phoneButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tengxun_register_button:
			break;
		case R.id.sina_register_button:
			break;
		case R.id.phone_register_button:
			break;
		case R.id.back_register_button:
			RegisterMainActivity.this.finish();
			break;
		}
	}
	
	

}
