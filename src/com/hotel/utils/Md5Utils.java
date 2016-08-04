package com.hotel.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.bmob.v3.c.i;

public class Md5Utils {
	public static String md5Password(String password){
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(password.getBytes());
			StringBuffer buffer = new StringBuffer();
			for(byte b :result){
				//& caculate
				int number = b & 0xff;
				String str = Integer.toHexString(number);
				if(str.length() == 1){
					buffer.append("0");
				}
				buffer.append(str);
			}
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public static String md5Password32(String password){
		String result = password;
		for(int i = 0;i < 32;i++){
			result = md5Password(result);
		}
		return result;
	}
}
