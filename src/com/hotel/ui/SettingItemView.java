package com.hotel.ui;




import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hotel.hoteldlnu.R;

/*
 * 自定义组合控件，他有两个TextView、ImageView、CheckBox、View（UnderLine）
 */

public class SettingItemView extends RelativeLayout {

	private CheckBox cb_state;
	private ImageView tip_mv;
	private TextView tv_setting_tip;
	private TextView tv_setting_title;
	private String desc_on, desc_off,title;

	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		inView(context);
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		inView(context);
		title = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.hotel.hoteldlnu",
				"title_tip");
		desc_on = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.hotel.hoteldlnu",
				"desc_on");
		desc_off = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.hotel.hoteldlnu",
				"desc_off");
		String mvres = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.hotel.hoteldlnu",
				"mv_icon");
		tv_setting_title.setText(title);
		tv_setting_tip.setText(desc_off);
		tip_mv.setBackgroundResource(PublicUiUtils.getDrawable(context.getResources(), mvres));
	}

	private void inView(Context context) {
		// TODO Auto-generated method stub
		View.inflate(context, R.layout.setting_item_view, this);

		this.cb_state = (CheckBox) this.findViewById(R.id.cb_state);
		this.tv_setting_tip = (TextView) this.findViewById(R.id.tv_setting_tip);
		this.tv_setting_title = (TextView) this
				.findViewById(R.id.tv_setting_title);
		this.tip_mv = (ImageView) this.findViewById(R.id.tip_mv);
	}

	public SettingItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		inView(context);
	}

	/*
	 * 校验是否选中
	 */
	public boolean isChecked() {
		return this.cb_state.isChecked();
	}
	
	public void setTipImage(int imageId){
		this.tip_mv.setBackgroundResource(imageId);
	}

	/*
	 * 设置组合控件的状态
	 */
	public void setChecked(boolean checked) {
		if (checked) {
			tv_setting_tip.setText(desc_on);
		} else {
			tv_setting_tip.setText(desc_off);
		}
		this.cb_state.setChecked(checked);
	}
	
	public void setTextViewTipColor(int color){
		tv_setting_tip.setTextColor(color);
	}

	/*
	 * 设置组合控件的描述信息
	 */

	public void setTextViewTip(String text) {
		tv_setting_tip.setText(text);
	}
	
}
