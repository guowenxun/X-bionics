package com.imcore.wenxun.UI;

import java.util.HashMap;
import java.util.Map;

import com.imcore.wenxun.HTTP.HttpHelper;
import com.imcore.wenxun.HTTP.RequestEntity;
import com.imcore.wenxun.HTTP.ResponseJsonEntity;
import com.imcore.wenxun.util.ConnectivityUtil;
import com.imcore.wenxun.util.JsonUtil;
import com.imcore.wenxun.util.TextUtil;
import com.imcore.wenxun.util.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class TribeLoginActivity extends Activity implements OnClickListener{
	
	private EditText userEditText,passwordEditText;
	private Button enterButton,forgerButton,backButton;
	public static String userId;
	public static String token;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tribelogin_activity);
		
		userEditText=(EditText) findViewById(R.id.user_login);
		passwordEditText=(EditText) findViewById(R.id.password_login);
		enterButton=(Button) findViewById(R.id.enter_tribe_button);
		forgerButton=(Button) findViewById(R.id.forget_password_button);
		backButton=(Button) findViewById(R.id.back_button);
		
		enterButton.setOnClickListener(this);
		forgerButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.enter_tribe_button:
			doLogin();
			break;
		case R.id.forget_password_button:
			forgetPassword();
			break;
		case R.id.back_button:
			TribeLoginActivity.this.finish();
			break;
		}
	}
	
        private void forgetPassword() {
		
	    }

		
		protected void doLogin() {
		  
			if (ConnectivityUtil.isOnline(this)) {
				String UserName = userEditText.getText().toString();
				String Password = passwordEditText.getText().toString();
				
				new LoginTask(UserName, Password).execute();
			} else {
				ToastUtil.showToast(TribeLoginActivity.this, "没有网络连接");
			}

		}
		
		// 构造新的异步任务类，通过构造函数传递参数数据
		class LoginTask extends AsyncTask<Void, Void, String> {
			private String	mUserName;
			private String	mPassword;

			public LoginTask(String User, String password) {
				mUserName = User;
				mPassword = password;
			}

			@Override
			protected String doInBackground(Void... params) {
				String url = "/passport/login.do";
				
				Map<String, Object> args = new HashMap<String, Object>();
				args.put("phoneNumber", mUserName);
				args.put("password", mPassword);
				args.put("client", "android");
				args.put("device", "sent");
				
				RequestEntity entity = new RequestEntity(url, args);
				String jsonResponse = null;
				try {
					jsonResponse = HttpHelper.execute(entity);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return jsonResponse;
			}

			@Override
			protected void onPostExecute(String result) {
				if (TextUtil.isEmptyString(result)) {
					ToastUtil.showToast(TribeLoginActivity.this, "输入为空");
					return;
				}
				ResponseJsonEntity resEntity = ResponseJsonEntity.fromJSON(result);

				if (resEntity.getStatus() == 200) {
					String jsonData = resEntity.getData();
					userId = JsonUtil.getJsonValueByKey(jsonData, "id");
					token = JsonUtil.getJsonValueByKey(jsonData, "token");
					
					SharedPreferences preferences = getSharedPreferences("config",
							MODE_PRIVATE);
					if (!preferences.getString("userId", "").equals(mUserName)) {
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString("username", mUserName);
						editor.putString("password", mPassword);
						editor.commit();
					}
					
					Intent intent = new Intent(TribeLoginActivity.this,
							FirstPagerActivity.class);
					startActivity(intent);
				} else {
					ToastUtil.showToast(TribeLoginActivity.this, resEntity.getMessage());
				}
			}
		}

}
