package com.hotel.hoteldlnu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

import com.hotel.constnats.Constants;
import com.hotel.dao.UserMessageInfo;
import com.hotel.net.NetUntils;
import com.hotel.ui.ShowDialog;
import com.hotel.utils.Md5Utils;

public class UserLogin extends Activity {

	private ImageView user_login_icon;
	private Button user_login,user_register;
	private TextView user_login_autograph; 
	private EditText user_login_password,user_login_name;
	private CheckBox user_login_checkbox;
	private SharedPreferences sp;
	private int count = 0;
	
	private ShowDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login_layout_activity);
		
		user_login_icon = (ImageView) this.findViewById(R.id.user_login_icon);
		user_login = (Button) this.findViewById(R.id.user_login);
		user_register = (Button) this.findViewById(R.id.user_register);
		user_login_autograph = (TextView) this.findViewById(R.id.user_login_autograph);
		user_login_name = (EditText) this.findViewById(R.id.user_login_name);
		user_login_password = (EditText) this.findViewById(R.id.user_login_password);
		user_login_checkbox = (CheckBox) this.findViewById(R.id.user_login_checkbox);
		
		Bmob.initialize(this, Constants.BMOB_API_ID);
		
		initialize();
	}

	private void initialize() {
		sp = getSharedPreferences("config",MODE_PRIVATE);
		
		boolean isRemember = sp.getBoolean("Is_Remember_PW", false);
		String userId = sp.getString("User_ID", null);
		if(isRemember){
			if(userId != null){
				user_login_name.setText(userId);
			}
			user_login_checkbox.setChecked(true);
		}else{
			Editor editor = sp.edit();
			editor.putString("User_ID", null);
			editor.commit();
			user_login_checkbox.setChecked(false);
			user_login_name.setText("");
		}
		
		user_login.setOnClickListener(listener);
		user_register.setOnClickListener(listener);
		user_login_checkbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Editor editor = sp.edit();
				editor.putBoolean("Is_Remember_PW", isChecked);
				editor.commit();
			}
		});
	}
	
	/**
	 * 事件处理
	 */
	private OnClickListener listener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			Button btn = (Button) v;
			if(btn == user_login){
				if(NetUntils.isNetworkAvailable(UserLogin.this)){
					login();
				}else{
					Toast.makeText(getApplicationContext(), "网络不可用，请检查网络！", 0).show();
				}
				
			}else if(user_register == btn ){
				Intent intent = new Intent(getApplicationContext(),UserRegister.class);
				startActivity(intent);
			}
		}
	};
	
	private void login() {
		//防止用户多次点击，
		if(count >= 3){
			Toast.makeText(this, "你应该用锤子来点我！", 0).show();
			count = 0;
			return;
		}
		
		String user_Id = user_login_name.getText().toString().trim();
		String userPW = user_login_password.getText().toString().trim();
		if(user_Id.equals("") || userPW.equals("")){
			Toast.makeText(getApplicationContext(), "账号或密码不能为空!",0).show();
			count++;
			return;
		}
		if(userPW.length() < 6){
			Toast.makeText(getApplicationContext(), "密码至少为6位!", 0).show();
			count++;
			return;
		}	
		dialog = new ShowDialog(this);
		dialog.createDialog("登录中...");
		
		userLogin(user_Id,userPW);

	}
	
	private void userLogin(final String user_Id, String userPW) {
		final String md5PW = Md5Utils.md5Password32(userPW);
		UserMessageInfo user = new UserMessageInfo();
		user.setUsername(user_Id);
		user.setPassword(md5PW);
		user.login(this, new SaveListener() {
		    @Override
		    public void onSuccess() {
		    	
		    	try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	dialog.closeDialog();
		    	
		    	Editor editor = sp.edit();
				editor.putString("User_ID", user_Id);
				editor.putString("User_Password", md5PW);
				editor.putBoolean("isLogin", true);
				editor.commit();	
		    	Toast.makeText(getApplicationContext(), "登陆成功！", 0).show();
		    	Intent intent = new Intent(UserLogin.this,
						HotelMainActivity.class);
				startActivity(intent);
				UserLogin.this.finish();
		    }
		    @Override
		    public void onFailure(int code, String msg) {
		    	dialog.closeDialog();
		    	Toast.makeText(getApplicationContext(), "登陆失败！用户名或密码不正确！", 0).show();
		    }
		});
	}

	/**
	 * 忘记密码处理函数
	 */
	public void forgetPassword(View view){
		Toast.makeText(this, "由于技术条件和时间有限，此功能暂时无法提供服务！", 0).show();
	}
}
