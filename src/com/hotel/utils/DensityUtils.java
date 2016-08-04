package com.hotel.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

public class DensityUtils {
	/**
	 * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 将字符串日期转化为日期
	 * @param date
	 * @return
	 */
	public static Date transLateStringToDate(String date){
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 提取字符传中的数字
	 * 
	 * @param args
	 */
	public static String subString(String args) {
		String str = "";
		Pattern p = Pattern.compile("[0-9\\.]+");
		Matcher m = p.matcher(args);
		while (m.find()) {
			str += m.group();
		}
		return str;
	}

	/**
	 * 获取系统的时间
	 * 
	 * @return	String
	 */
	public static String getSystemTime() {
		String time;
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		time = format.format(date);
		return time;
	}

	/**
	 * 获取系统的日期
	 * 
	 * @return
	 */
	public static String getSystemDate() {
		String time;
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = new Date(System.currentTimeMillis());
		time = format.format(date);
		return time;
	}

	/**
	 * 
	 */
	public static Date StrToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 构造一个随机日期
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Date randomDate(String beginDate, String endDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
			Date start = format.parse(beginDate);// 构造开始日期
			Date end = format.parse(endDate);// 构造结束日期
			if (start.getTime() >= end.getTime()) {
				return null;
			}
			long date = random(start.getTime(), end.getTime());

			return new Date(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static long random(long begin, long end) {
		long rtn = begin + (long) (Math.random() * (end - begin));
		// 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
		if (rtn == begin || rtn == end) {
			return random(begin, end);
		}
		return rtn;
	}

	/**
	 * 判断是否为合法的电话号码
	 * @param phoneNumber 将要判断号码
	 * @return
	 */
	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;

		String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		CharSequence inputStr = phoneNumber;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		}

		return isValid;
	}

	/**
	 * 判断邮箱是否合法
	 * @param email 将要判断的邮箱
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	/*
	 * Integer.parseInt(String s) Long.parseLong(String s)
	 * Float.parseFloat(String s) Double.parseDouble(String s)
	 */
	
	/**
	 * 判断字符是否全为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (str.length() != 10)
			return false;
		else {
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(str);
			if (!isNum.matches())
				return false;
		}
		return true;
	}
	
	/**
	 * 将网络中的图片路径中文件名分离出来(包括文件扩展名)
	 * @param fileName
	 * @return
	 */
	public static String divisionFileName(String fileUrl){
		if(fileUrl == null)
			return "2";
		String temp = "http://newfile.codenow.cn:8080/";
		String result = fileUrl.substring(temp.length(), fileUrl.length());
		return result;
	}
	
	/**
	 * 分离缓存中文件路径中的文件名
	 * @param fileName
	 * @return
	 */
	public static String divisionCacheFileName(String fileUrl){
		if(fileUrl == null){
			return "1";
		}
		File file = new File(fileUrl);
		if(file.exists()){
			return file.getName();
		}
		return "0";
	}
	
	/**
	 * 判断原文件名是否与缓存文件名一致
	 * @param originalfile
	 * @param cacheFile
	 * @return
	 */
	public static boolean judgeFileSame(String originalfile,String cacheFile){
		final String fileName = DensityUtils.divisionFileName(originalfile);
		String cacheFileName = DensityUtils.divisionCacheFileName(cacheFile);
		if(fileName.equals(cacheFileName)){
			return true;
		}
		return false;
	}

}
