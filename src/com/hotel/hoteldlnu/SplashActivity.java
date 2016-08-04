package com.hotel.hoteldlnu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hotel.utils.GetAPPMessage;

public class SplashActivity extends Activity {

	private RelativeLayout splash_rel;
	private TextView tv_splash_version;
	private long durationMillis = 1500;
	private long startTime = 2000;
	private SharedPreferences sp;
	private boolean isLogin, is_auto_login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		setContentView(R.layout.aplashp_activity);

		sp = getSharedPreferences("config", MODE_PRIVATE);
		isLogin = sp.getBoolean("isLogin", false);
		is_auto_login = sp.getBoolean("is_auto_login", false);

		this.splash_rel = (RelativeLayout) this.findViewById(R.id.splash_rel);
		this.tv_splash_version = (TextView) this
				.findViewById(R.id.tv_splash_version);

		this.tv_splash_version.setText("版本 " + GetAPPMessage.getVersion(this));

		AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
		animation.setDuration(durationMillis);
		this.splash_rel.startAnimation(animation);

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				enterHome();
			}
		}, startTime);
	}

	/**
	 * 进入程序主界面
	 */
	protected void enterHome() {
		if (is_auto_login) {
			if (isLogin) {
				Intent intent = new Intent(SplashActivity.this,
						HotelMainActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();
				return;
			}
		}
		Intent intent = new Intent(SplashActivity.this, UserLogin.class);
		startActivity(intent);
		SplashActivity.this.finish();
	}

	/**
	 * 进行界面的刷新
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};

}
