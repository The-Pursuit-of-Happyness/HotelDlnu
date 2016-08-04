package com.hotel.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotel.constnats.Constants;
import com.hotel.dao.MessMenu;
import com.hotel.hoteldlnu.R;
import com.hotel.temp.LoadImage;
import com.hotel.temp.LoadImage.GetImageBitmap;

public class MessUpdateAdapter extends BaseAdapter {
	
	private Activity context;
	private List<MessMenu> messInfo;
	
	public MessUpdateAdapter(Activity context,List<MessMenu> messInfo){
		this.context = context;
		this.messInfo = messInfo;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return messInfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final TextViewHolder holder;
		View view;
		if(convertView != null){
			view = convertView;
			holder = (TextViewHolder) convertView.getTag();
		}else{
			holder = new TextViewHolder();
			 view = View.inflate(context, R.layout.menu_management_item, null);
			holder.messimg = (ImageView) view.findViewById(R.id.messimg);
			holder.messname = (TextView) view.findViewById(R.id.messname);
			holder.messdescribe = (TextView) view.findViewById(R.id.messdescribe);
			
			view.setTag(holder);
		}
		holder.messimg.setBackgroundResource(R.drawable.loading);
		LoadImage.LoadImageFile(context,
				Constants.LOADING_SAMLL_IMAGE, messInfo.get(position).getMess_Graph(),
				new GetImageBitmap() {
					@Override
					public void getImageBitmap(Bitmap bitmap, String cacheUrl) {
						holder.messimg.setBackgroundColor(Color.WHITE);
						holder.messimg.setImageBitmap(bitmap);
					}
				});
		holder.messname.setText(messInfo.get(position).getMess_Name());
		holder.messdescribe.setText(messInfo.get(position).getMess_Describe());
		
		return view;
	}

}
