package com.hotel.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hotel.hoteldlnu.R;

/*
 * 自定义组合控件，他有两个TextView、CheckBox、View（UnderLine）
 */


public class SettingClickView extends RelativeLayout {
	
	private TextView tv_setting_tip;
	private TextView tv_setting_title;
	private ImageView tip_mv;
	
	public SettingClickView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle); 
		// TODO Auto-generated constructor stub
		inView(context);
	}

	public SettingClickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		inView(context);
		String title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.hotel.hoteldlnu", "title_tip");
		String mvres = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.hotel.hoteldlnu", "mv_icon");
		tv_setting_title.setText(title);
		tip_mv.setBackgroundResource(PublicUiUtils.getDrawable(context.getResources(), mvres));
	}

	private void inView(Context context) {
		// TODO Auto-generated method stub
		View.inflate(context, R.layout.setting_click_view,this);
		
		this.tv_setting_tip = (TextView)this.findViewById(R.id.tv_setting_tip);
		this.tv_setting_title = (TextView)this.findViewById(R.id.tv_setting_title);
		this.tip_mv = (ImageView) this.findViewById(R.id.tip_mv);
	}

	public SettingClickView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		inView(context);
	}
	
	public void setTextViewTipColor(int color){
		tv_setting_tip.setTextColor(color);
	}
	
	/*
	 * 设置组合控件的描述信息
	 */
	public void setTextViewTip(String text){
		tv_setting_tip.setText(text);
	}
}
