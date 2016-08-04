package com.hotel.itemactivity;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;

import com.hotel.constnats.Constants;
import com.hotel.hoteldlnu.R;
import com.hotel.net.NetUntils;
import com.hotel.services.CleanCacheFile;
import com.hotel.services.ServiceUtils;
import com.hotel.ui.SettingClickView;
import com.hotel.ui.SettingItemView;
import com.hotel.utils.DensityUtils;

public class Setting extends Activity {

	private SharedPreferences sp;
	private SettingItemView siv_login, siv_clear_cache, siv_clear_bell;
	private SettingClickView siv_set_netWork, siv_set_clearTime,
			siv_set_systemTime, siv_set_clearFolder;
	private Intent services;
	private int item;
	private boolean isRefresh;
	private RefreshTime refreshThread;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_setting);

		siv_login = (SettingItemView) this.findViewById(R.id.siv_login);
		siv_clear_bell = (SettingItemView) this
				.findViewById(R.id.siv_clear_bell);
		siv_clear_cache = (SettingItemView) this
				.findViewById(R.id.siv_clear_cache);
		siv_set_netWork = (SettingClickView) this
				.findViewById(R.id.siv_set_netWork);
		siv_set_systemTime = (SettingClickView) this
				.findViewById(R.id.siv_set_systemTime);
		siv_set_clearTime = (SettingClickView) this
				.findViewById(R.id.siv_set_clearTime);
		siv_set_clearFolder = (SettingClickView) this
				.findViewById(R.id.siv_set_clearFolder);

		sp = this.getSharedPreferences("config", MODE_PRIVATE);
		isRefresh = true;

		fillDate();

		addListener();
	}

	private void fillDate() {
		// 加载自动登录
		boolean isLoing = sp.getBoolean("is_auto_login", true);
		if (isLoing) {
			siv_login.setChecked(true);
		} else {
			siv_login.setChecked(false);
		}
		// 加载自动清理缓存
		services = new Intent(Setting.this, CleanCacheFile.class);
		boolean isCallServiceRunning = ServiceUtils.isServiceRunning(
				Setting.this, "com.mess.ordermess.services.CleanCacheFile");
		if (isCallServiceRunning) {
			siv_clear_cache.setChecked(true);
			this.startService(services);
		} else {
			siv_clear_cache.setChecked(false);
			this.stopService(services);
		}

		// setting system bell
		boolean isBellOpen = sp.getBoolean("System_Bell", true);
		if (isBellOpen) {
			siv_clear_bell.setChecked(true);
		} else {
			siv_clear_bell.setChecked(false);
		}

		// 显示网络设置状态
		refreshNetState();

		// setting system time
		refreshThread = new RefreshTime();
		refreshThread.start();

		// setting auto clear cache time
		item = sp.getInt("Clear_Cache_Circle", 3);
		siv_set_clearTime.setTextViewTip(Constants.CLEAR_CACHE_TIME[item]);

		// open cache folder
		siv_set_clearFolder.setTextViewTip(Constants.mSdRootPath
				+ Constants.FOLDER_NAME + File.separator);

	}

	private void refreshNetState() {
		if (NetUntils.isNetworkAvailable(this)) {
			siv_set_netWork.setTextViewTip("网络状态良好");
			siv_set_netWork.setTextViewTipColor(getResources().getColor(
					R.color.list_item_Ablack));
		} else {
			siv_set_netWork.setTextViewTipColor(Color.RED);
			siv_set_netWork.setTextViewTip("网络不可用");
		}
	}

	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constants.REFRESH_SYSTEM_TIME:
				refreshSystemTime();
				refreshNetState();
				break;
			default:
				break;
			}
		}
	};

	// refresh system thread
	private class RefreshTime extends Thread {
		public void run() {
			while (isRefresh) {
				Message handlerMsg = handler.obtainMessage();
				handlerMsg.what = Constants.REFRESH_SYSTEM_TIME;
				handler.sendMessage(handlerMsg);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void refreshSystemTime() {
		String str = DensityUtils.getSystemTime();
		siv_set_systemTime.setTextViewTip(str);
	}

	private void addListener() {
		// 监听自动登录
		siv_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Editor edit = sp.edit();
				if (siv_login.isChecked()) {
					siv_login.setChecked(false);
					edit.putBoolean("isLogin", false);
				} else {
					siv_login.setChecked(true);
					edit.putBoolean("isLogin", true);
				}
				edit.putBoolean("is_auto_login", siv_login.isChecked());
				edit.commit();
			}
		});

		// 监听自动清理缓存
		siv_clear_cache.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				item = sp.getInt("Clear_Cache_Circle", 3);
				if (4 == item) {
					natification();
					siv_clear_cache.setChecked(false);
				} else {
					if (siv_clear_cache.isChecked()) {
						siv_clear_cache.setChecked(false);
						Setting.this.stopService(services);
					} else {
						Setting.this.startService(services);
						siv_clear_cache.setChecked(true);
					}
					Editor edit = sp.edit();
					edit.putBoolean("is_auto_clear",
							siv_clear_cache.isChecked());
					edit.commit();
				}
			}
		});

		// setting System bell
		siv_clear_bell.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (siv_clear_bell.isChecked()) {
					siv_clear_bell.setChecked(false);
				} else {
					siv_clear_bell.setChecked(true);
				}
				Editor edit = sp.edit();
				edit.putBoolean("System_Bell", siv_clear_bell.isChecked());
				edit.commit();
			}
		});

		// 打开网络设置
		siv_set_netWork.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
				Setting.this.startActivity(intent);
			}
		});

		// open system time setting
		siv_set_systemTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
				Setting.this.startActivity(intent);
				refreshSystemTime();
			}
		});

		// 设置自动清理缓存周期
		siv_set_clearTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showSecletClearCacheDialog();
			}
		});

		// open cache folder
		siv_set_clearFolder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String filePath = Constants.mSdRootPath + Constants.FOLDER_NAME;
				File file = new File(filePath);
				if (!file.exists()) {
					file.mkdir();
				}
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_GET_CONTENT);

				intent.setDataAndType(Uri.fromFile(file), "*/*");
				startActivity(intent);
			}
		});

	}

	protected void showSecletClearCacheDialog() {
		item = sp.getInt("Clear_Cache_Circle", 3);
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("自动清理缓存周期");
		builder.setSingleChoiceItems(Constants.CLEAR_CACHE_TIME, item,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						siv_set_clearTime
								.setTextViewTip(Constants.CLEAR_CACHE_TIME[which]);
						Editor edit = sp.edit();
						edit.putInt("Clear_Cache_Circle", which);
						edit.commit();
						dialog.dismiss();
					}
				});
		builder.setPositiveButton("取消", null);
		builder.show();
	}

	public void natification() {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.app_icon; // 通知图标
		String tickerText = "点餐宝服务通知";
		long when = System.currentTimeMillis(); // 通知产生的时间，会在通知信息里显示
		Notification notification = new Notification(icon, tickerText, when);
		CharSequence contentTitle = "点餐宝启动缓存清理服务失败！";
		CharSequence contentText = "失败原因：你已经设置为手动清理模式！";
		boolean isBellOpen = sp.getBoolean("System_Bell", true);
		if (isBellOpen) {
			notification.defaults = Notification.DEFAULT_SOUND;
		}
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		Intent appIntent = new Intent(Intent.ACTION_MAIN);
		appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		appIntent.setComponent(new ComponentName(this.getPackageName(), this
				.getPackageName() + "." + this.getLocalClassName()));
		appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				appIntent, 0);
		notification.setLatestEventInfo(this, contentTitle, contentText,
				contentIntent);
		mNotificationManager.notify(1, notification);
	}

	@Override
	protected void onDestroy() {
		isRefresh = false;
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		isRefresh = false;
		super.onBackPressed();
	}

	public void Back(View view) {
		isRefresh = false;
		this.finish();
	}
}
