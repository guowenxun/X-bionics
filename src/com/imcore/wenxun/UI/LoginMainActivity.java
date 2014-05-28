package com.imcore.wenxun.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginMainActivity extends Activity implements OnClickListener{
	
	private Button tengxunButton,xinlangButton,newuserButton,buluoButton,helpButton,serviceButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		tengxunButton=(Button) findViewById(R.id.tengxun_button);
		xinlangButton=(Button) findViewById(R.id.xinlang_button);
		newuserButton=(Button) findViewById(R.id.newuser_button);
		buluoButton=(Button) findViewById(R.id.buluo_button);
		helpButton=(Button) findViewById(R.id.help_button);
		serviceButton=(Button) findViewById(R.id.service_button);
		
		tengxunButton.setOnClickListener(this);
		xinlangButton.setOnClickListener(this);
		newuserButton.setOnClickListener(this);
		buluoButton.setOnClickListener(this);
		helpButton.setOnClickListener(this);
		serviceButton.setOnClickListener(this);
  }

	@Override
	public void onClick(View view) {
       switch(view.getId()){
       case R.id.tengxun_button:
          Intent intent1=new Intent(LoginMainActivity.this,TXLoginActivity.class);
          startActivity(intent1);
    	   break;
       case R.id.xinlang_button:
    	   Intent intent2=new Intent(LoginMainActivity.this,XLLoginActivity.class);
           startActivity(intent2);
    	   break;
       case R.id.newuser_button:
    	   Intent intent3=new Intent(LoginMainActivity.this,RegisterMainActivity.class);
           startActivity(intent3);
    	   break;
       case R.id.buluo_button:
    	   Intent intent4=new Intent(LoginMainActivity.this,TribeLoginActivity.class);
           startActivity(intent4);
    	   break;
       case R.id.help_button:
    	   Intent intent5=new Intent(LoginMainActivity.this,HelpActivity.class);
           startActivity(intent5);
    	   break;
       case R.id.service_button:
    	   Intent intent6=new Intent(LoginMainActivity.this,ServiceItemActivity.class);
           startActivity(intent6);
    	   break;
       }		
	}
	
	
}