package com.hotel.constnats;

import android.os.Environment;

public class Constants {
	
	public static String BMOB_API_ID = "b7af65d596b5d5d543ae0271aee8c224";
	public static String BMOB_API_ACCESSKEY = "981551412b1011855f230d14a4e64668";
	
	public final static int USER_ORDER_DOWNLOAD = 0x0001;
	public final static int USERE_ORDER_NEW = 0x0002;
	public final static int USER_ORDER_ACCOMPLE = 0x0003;
	public final static int USER_ORDER_DOWNLOAD_ERROR = 0x0004;
	public final static int MESS_DOWNLOAD_SUCCESS = 0x0005;
	public final static int MESS_DOWNLOAD_ERROR = 0x0006;
	
	public final static String PICTURE_YUAN = "big_";
	public static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();  
	public final static String FOLDER_NAME = "/ClientDlnu";
	public static int LOADING_SAMLL_IMAGE = 0x0012;
	public static int LOADING_BIG_IMAGE = 0x0013;
	
	public final static int REFRESH_SYSTEM_TIME = 0x0018;
	
	public static String CLEAR_CACHE_TIME[] = {
		"15分钟","30分钟","1小时","24小时","手动清理"
	} ;
}
