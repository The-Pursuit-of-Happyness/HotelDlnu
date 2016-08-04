package com.hotel.itemactivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.constnats.Constants;
import com.hotel.dao.MessMenu;
import com.hotel.hoteldlnu.R;
import com.hotel.temp.LoadImage;
import com.hotel.temp.LoadImage.GetImageBitmap;

public class MenuManagementItemActivity extends Activity {
	
	private MessMenu messInfo;
	private TextView show_mess_describe,show_mess_name,show_mess_price,show_mess_sale,
	show_mess_feature,show_mess_effect;
	private RatingBar show_mess_star;
	private ImageView show_mess_img;
	private ActionBar abtitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_management_item_in);
		abtitle = this.getActionBar();
		abtitle.setDisplayHomeAsUpEnabled(true);
		abtitle.setHomeButtonEnabled(true);
		show_mess_name = (TextView) this.findViewById(R.id.show_mess_name);
		show_mess_price = (TextView) this.findViewById(R.id.show_mess_price);
		show_mess_sale = (TextView) this.findViewById(R.id.show_mess_sale);
		show_mess_describe = (TextView) this.findViewById(R.id.show_mess_describe);
		show_mess_feature = (TextView) this.findViewById(R.id.show_mess_feature);
		show_mess_effect = (TextView) this.findViewById(R.id.show_mess_effect);
		show_mess_star = (RatingBar) this.findViewById(R.id.show_mess_star);
		show_mess_img = (ImageView) this.findViewById(R.id.show_mess_img);
		
		getMessData();
		fillData();
	}

	private void fillData() {
		abtitle.setTitle(messInfo.getMess_Name());
		show_mess_name.setText(messInfo.getMess_Name());
		show_mess_price.setText("价格:"+messInfo.getMess_Price().toString()+"元");
		show_mess_sale.setText("销量:"+messInfo.getSales_Volume().toString());
		show_mess_star.setRating(messInfo.getMess_Start());
		show_mess_describe.setText(messInfo.getMess_Describe());
		show_mess_feature.setText(messInfo.getMess_Feature());
		show_mess_effect.setText(messInfo.getMess_Effect());
		show_mess_img.setBackgroundResource(R.drawable.loading);
		LoadImage.LoadImageFile(MenuManagementItemActivity.this,
				Constants.LOADING_SAMLL_IMAGE, messInfo.getMess_Graph(),
				new GetImageBitmap() {
					@Override
					public void getImageBitmap(Bitmap bitmap, String cacheUrl) {
						show_mess_img.setBackgroundColor(Color.WHITE);
						show_mess_img.setImageBitmap(bitmap);
					}
				});
	}

	private void getMessData() {
		Intent intent = this.getIntent();
		messInfo = intent.getParcelableExtra("com.mess.ordermess.dao.MessMenu");
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.changemess, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		if(item.getItemId() ==R.id.action_changemess)
			{
				Toast.makeText(this, "修改", Toast.LENGTH_SHORT).show();
		    }
		return super.onOptionsItemSelected(item);
	}
	
}
