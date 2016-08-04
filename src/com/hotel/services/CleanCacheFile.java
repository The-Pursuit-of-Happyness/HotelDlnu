package com.hotel.services;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.hotel.utils.FileUtils;

public class CleanCacheFile extends Service {

	private Timer timer;
	private FileUtils fileUtils;
	private long when = 600000;
	private SharedPreferences sp;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		fileUtils = new FileUtils(this);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		int valueItem = sp.getInt("Clear_Cache_Circle", 3);
		long clearCacheTime;
		if (0 == valueItem) {
			clearCacheTime = 15 * 60 * 1000;
		} else if (1 == valueItem) {
			clearCacheTime = 30 * 60 * 1000;
		} else if (2 == valueItem) {
			clearCacheTime = 1 * 60 * 60 * 1000;
		} else {
			clearCacheTime = 24 * 60 * 60 * 1000;
		}
		when = clearCacheTime;
		timer = new Timer(true);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (null == timer) {
			timer = new Timer(true);
		}

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				fileUtils.deleteFile();
			}

		}, when);
		// return START_STICKY_COMPATIBILITY;
		// return super.onStartCommand(intent, flags, startId);
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}
}
