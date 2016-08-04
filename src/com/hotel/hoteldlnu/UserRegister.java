package com.hotel.hoteldlnu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

import com.hotel.constnats.Constants;
import com.hotel.dao.UserMessageInfo;
import com.hotel.net.NetUntils;
import com.hotel.ui.ShowDialog;
import com.hotel.utils.DensityUtils;
import com.hotel.utils.Md5Utils;

public class UserRegister extends Activity {

	private Button user_register;
	private EditText user_register_password, user_register_name,
			user_register_pw_ok;
	private ShowDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_user_register_activity);
		user_register = (Button) this.findViewById(R.id.user_register);
		user_register_password = (EditText) this
				.findViewById(R.id.user_register_password);
		user_register_name = (EditText) this
				.findViewById(R.id.user_register_name);
		user_register_pw_ok = (EditText) this
				.findViewById(R.id.user_register_pw_ok);

		Bmob.initialize(this, Constants.BMOB_API_ID);

		initialize();
	}

	private void initialize() {
		user_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ((Button) v == user_register) {
					if(NetUntils.isNetworkAvailable(UserRegister.this)){
						register();
					}else{
						Toast.makeText(getApplicationContext(), "���粻���ã��������磡", 0).show();
					}
				}
			}

		});
	}

	private void showRegisterDialog() {
		dialog = new ShowDialog(this);
		dialog.createDialog("����ע��...");
	}

	private void register() {
		String userName = user_register_name.getText().toString().trim();
		String userPW = user_register_password.getText().toString().trim();
		String userPwOk = user_register_pw_ok.getText().toString().trim();
		if (userName.equals("") || userPW.equals("")) {
			Toast.makeText(getApplicationContext(), "�û��������벻��Ϊ��!", 0).show();
			return;
		}
		if(userName.indexOf("@") != -1){
			if(!DensityUtils.isEmail(userName)){
				Toast.makeText(getApplicationContext(), "�绰�����ʽ����ȷ!", 0).show();
				return;
			}
		}else if(!DensityUtils.isPhoneNumberValid(userName)){
			Toast.makeText(getApplicationContext(), "�����ʽ����ȷ!", 0).show();
			return;
		}
		
		if (userPW.length() < 6) {
			Toast.makeText(getApplicationContext(), "��������Ϊ6λ!", 0).show();
			return;
		}
		if (!userPW.equals(userPwOk)) {
			Toast.makeText(getApplicationContext(), "ǰ�����벻һ��!", 0).show();
			return;
		}
		showRegisterDialog();
		getUserMessage(userName, userPwOk);
	}

	public void getUserMessage(final String userName, final String userPw) {

		String md5UserPW = Md5Utils.md5Password32(userPw);

		final UserMessageInfo registerUser = new UserMessageInfo();
		
		registerUser.setUsername(userName);
		registerUser.setPassword(md5UserPW);

		registerUser.signUp(UserRegister.this, new SaveListener() {
			@Override
			public void onSuccess() {
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				dialog.closeDialog();
				Toast.makeText(getApplicationContext(), "ע��ɹ�!", 0).show();
				UserRegister.this.finish();
			}

			@Override
			public void onFailure(int code, String msg) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				dialog.closeDialog();
				Toast.makeText(getApplicationContext(),
						"ע��ʧ��!�������û����Ѿ����ڻ����������ӳ�ʱ!"+code+msg, 0).show();
			}
		});

	}

	public void userRegisterBack(View view) {
		this.finish();
	}
}
