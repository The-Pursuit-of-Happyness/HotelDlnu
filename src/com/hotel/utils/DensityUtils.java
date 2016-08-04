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
	 * �����ֻ��ķֱ��ʴ� dip �ĵ�λ ת��Ϊ px(����)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * ���ַ�������ת��Ϊ����
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
	 * ��ȡ�ַ����е�����
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
	 * ��ȡϵͳ��ʱ��
	 * 
	 * @return	String
	 */
	public static String getSystemTime() {
		String time;
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		time = format.format(date);
		return time;
	}

	/**
	 * ��ȡϵͳ������
	 * 
	 * @return
	 */
	public static String getSystemDate() {
		String time;
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd��");
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
	 * ����һ���������
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Date randomDate(String beginDate, String endDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
			Date start = format.parse(beginDate);// ���쿪ʼ����
			Date end = format.parse(endDate);// �����������
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
		// ������ص��ǿ�ʼʱ��ͽ���ʱ�䣬��ݹ���ñ������������ֵ
		if (rtn == begin || rtn == end) {
			return random(begin, end);
		}
		return rtn;
	}

	/**
	 * �ж��Ƿ�Ϊ�Ϸ��ĵ绰����
	 * @param phoneNumber ��Ҫ�жϺ���
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
	 * �ж������Ƿ�Ϸ�
	 * @param email ��Ҫ�жϵ�����
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
	 * �ж��ַ��Ƿ�ȫΪ����
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
	 * �������е�ͼƬ·�����ļ����������(�����ļ���չ��)
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
	 * ���뻺�����ļ�·���е��ļ���
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
	 * �ж�ԭ�ļ����Ƿ��뻺���ļ���һ��
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
