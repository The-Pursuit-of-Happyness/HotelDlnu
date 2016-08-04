package com.hotel.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hotel.dao.UserOrder;
import com.hotel.hoteldlnu.R;

public class UserOrderAdapter extends BaseAdapter {

	private Activity context;
	private int type;
	private List<UserOrder> userOrder;
	
	public UserOrderAdapter(Activity context,int type,List<UserOrder> userOrder){
		this.context = context;
		this.type = type;
		this.userOrder = userOrder;
	}
	
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return userOrder.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.activity_ordermessage_item, null);
		TextView username = (TextView) view.findViewById(R.id.username);
		TextView orderdate = (TextView) view.findViewById(R.id.orderdate);
		
		UserOrder order = userOrder.get(position);
		username.setText(order.getUser_Id());
		orderdate.setText(order.getCreatedAt());
		return view;
	}

}
