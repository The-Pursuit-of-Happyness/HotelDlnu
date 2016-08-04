package com.hotel.dao;

import com.hotel.hoteldlnu.R;


public class NavigationContent {
	
	/**
	 * 当用户没有设置头像时，为默认头像
	 */
	public static int defaultImage = R.drawable.img05;
	/**
	 * 菜单分类导航名称
	 */
	public static String[] menuNames = { 
		"小吃", "炒菜", "盖饭", "面食", "瓦罐",
		"糕点", "水饺","砂锅", "早餐", "麻辣烫", 
		"米线","烤肉拌饭", 
	};
	/**
	 * 每种菜对应的ID标识
	 */
	public static String[] MessClassifyID = {
		"0","1","2","3","4","5",
		"6","7","8","9","10","11"
	};
	/**
	 * 每种菜的logo
	 */
	public static int menuIcon[] = {
		R.drawable.xiaochi,R.drawable.chaocai,R.drawable.gaifan,
		R.drawable.mianshi,R.drawable.waguantang,R.drawable.gaodian,
		R.drawable.shuijiao,R.drawable.shaguo,R.drawable.zaocan,
		R.drawable.malatang,R.drawable.mixian,R.drawable.kaoroufan
	};
	
	
	/**
	 * 新闻主页滚动新闻图片的提示
	 */
	public static  String[] newsImageTip = { 
		"每日激情，某某上演最佳选手","天天有喜，某某与某某欢喜冤家","火辣，基本不是问题",
		"一周一日，青春不再回来","那些年，我一起追过的女孩"
	};
	
}
