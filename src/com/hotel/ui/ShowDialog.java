package com.hotel.ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hotel.hoteldlnu.R;

public class ShowDialog {
	
	private Context context;
	private AlertDialog dialog;
	
	public ShowDialog(Context context){
		this.context = context;		
	}
	
	public void createDialog(String tipStr){
		AlertDialog.Builder builder = new Builder(context);
		View view = View.inflate(context, R.layout.user_register_prograss, null);
		TextView dialog_temp_tip = (TextView) view.findViewById(R.id.temp_tip);
		dialog_temp_tip.setText(tipStr);
		builder.setCancelable(false);
		builder.setView(view);
		dialog = builder.show();
	}
	
	public void closeDialog(){
		dialog.dismiss();
	}
}
