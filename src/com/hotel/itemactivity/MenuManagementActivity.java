package com.hotel.itemactivity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.hotel.adapter.MessUpdateAdapter;
import com.hotel.constnats.Constants;
import com.hotel.dao.MessMenu;
import com.hotel.hoteldlnu.R;
import com.hotel.itemactivity.AddMessDialogFragment.InputListener;

public class MenuManagementActivity extends Activity  implements InputListener {

	private List<MessMenu> messInfo;
	private ListView MessList;
	private LinearLayout shop_mess_Loading;
	private MessUpdateAdapter messAdapter;
	private ActionBar abtitle ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		abtitle = this.getActionBar();
		abtitle.setTitle("菜单管理");
		abtitle.setDisplayHomeAsUpEnabled(true);
		abtitle.setHomeButtonEnabled(true);
		setContentView(R.layout.menu_management_activity);

		shop_mess_Loading = (LinearLayout) this.findViewById(R.id.shop_mess_Loading);
		MessList = (ListView) this.findViewById(R.id.lv_shop_mess);
		// getActionBar().setDisplayShowHomeEnabled(true);//设置是否显示应用程序的图标(向左的箭头)
		// getActionBar().setHomeButtonEnabled(true);//将应用程序图标设置为可点击的按钮
		getActionBar().setDisplayHomeAsUpEnabled(true);// 将应用程序图标设置为可点击的按钮,并且在图标上添加向左的箭头
		Bmob.initialize(this, "b7af65d596b5d5d543ae0271aee8c224");
		messInfo = new ArrayList<MessMenu>();
		shop_mess_Loading.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {
				getMessData();
			}
		}.start();
		
		addListener();

	}

	private void addListener() {
		MessList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = new Intent(MenuManagementActivity.this,
						MenuManagementItemActivity.class);
				intent.putExtra("com.mess.ordermess.dao.MessMenu", (Parcelable)messInfo.get(position));
				startActivity(intent);
			}
		});

	}

	private void getMessData() {
		BmobQuery<MessMenu> query = new BmobQuery<MessMenu>();
		query.addWhereEqualTo("shop_Id", "DLNU20157");
		query.findObjects(this, new FindListener<MessMenu>() {
			@Override
			public void onSuccess(List<MessMenu> object) {
				if(null == messInfo){
					messInfo = new ArrayList<MessMenu>();
					messInfo = object;
				}else{
					messInfo.addAll(object);
				}
				Message msg = new Message();
				msg.what = Constants.MESS_DOWNLOAD_SUCCESS;
				handler.sendMessage(msg);
			}

			@Override
			public void onError(int code, String msg1) {
				Message msg = new Message();
				msg.what = Constants.MESS_DOWNLOAD_ERROR;
				handler.sendMessage(msg);
			}
		});
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			shop_mess_Loading.setVisibility(View.INVISIBLE);
			switch(msg.what){
			case Constants.MESS_DOWNLOAD_SUCCESS:
				if(messAdapter == null){
					messAdapter = new MessUpdateAdapter(MenuManagementActivity.this,messInfo);
					MessList.setAdapter(messAdapter);
				}else{
					messAdapter.notifyDataSetChanged();
				}
				break;
			case Constants.MESS_DOWNLOAD_ERROR:
				
				break;
				default:
					break;
			}
		}
	};
	 public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.addmess, menu);
	        return true;
	    }
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		if(item.getItemId() ==R.id.action_addmess){
			AddMessDialogFragment addmess = new AddMessDialogFragment();
			addmess.show(getFragmentManager(), "");
	    }
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void InputComplete(String messname, String messprice,
			String messdescribe, String messfeature, String messeffect) {
		Toast.makeText(this, messname, Toast.LENGTH_SHORT).show();
		
	}
}
