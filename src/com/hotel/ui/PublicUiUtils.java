package com.hotel.ui;

import android.content.res.Resources;

import com.hotel.hoteldlnu.R;

public class PublicUiUtils {
	
	public static int getDrawable(Resources res, String fileName) {
		if (fileName.equals("Login_setting")) {
			return R.drawable.userlogo;
		}else if(fileName.equals("network_setting")){
			return R.drawable.wifi;
		}else if(fileName.equals("cache_setting_time")){
			return R.drawable.circle;
		}else if(fileName.equals("time_setting")){
			return R.drawable.time;
		}else if(fileName.equals("cache_setting")){
			return R.drawable.clearicon;
		}else if(fileName.equals("cache_setting_folder")){
			return R.drawable.cloud_down;
		}else if(fileName.equals("setting_sound")){
			return R.drawable.sound;
		}
		return R.drawable.app_icon;
	}
}
