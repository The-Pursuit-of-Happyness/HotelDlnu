package com.hotel.hoteldlnu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.hotel.adapter.ViewMyOrderHolder;
import com.hotel.constnats.Constants;
import com.hotel.dao.MessMenu;
import com.hotel.dao.NavigationContent;
import com.hotel.dao.ShopMsgInfo;
import com.hotel.dao.UserMessageInfo;
import com.hotel.dao.UserOrder;
import com.hotel.itemactivity.CallUs;
import com.hotel.itemactivity.MenuManagementActivity;
import com.hotel.itemactivity.MyMessage;
import com.hotel.itemactivity.Setting;
import com.hotel.temp.LoadImage;
import com.hotel.temp.LoadImage.GetImageBitmap;
import com.hotel.utils.DensityUtils;

public class HotelMainActivity extends TabActivity implements OnItemClickListener {

	private int[] imageid = new int[] {  R.drawable.mymess,
			 R.drawable.myself, R.drawable.setting,R.drawable.callus };
	private String[] menu = new String[] { "菜单管理",  "信息管理",
			"系统设置","关于我们" };
	
	private String dealOrder = "已处理";
	private String newOrder = "新消息";

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList, newmessagelist, oldmessagelist;
	private ActionBarDrawerToggle mDrawerToggle;
	private List<Map<String, Object>> listitems;
	private String mTitle;
	private LinearLayout order_mess_Loading;
	private UserOrderAdapter adapter;
	private TabHost tabhost;
	private int tabtype = 1;

	private List<UserOrder> userOrder;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		order_mess_Loading = (LinearLayout) this
				.findViewById(R.id.order_mess_Loading);
		newmessagelist = (ListView) findViewById(R.id.newmessagelist);
		order_mess_Loading.setVisibility(View.VISIBLE);

		userOrder = new ArrayList<UserOrder>();

		Bmob.initialize(this, Constants.BMOB_API_ID);

		getData(Constants.USER_ORDER_DOWNLOAD);

		oldmessagelist = (ListView) findViewById(R.id.oldmessagelist);

		// 向两个选项卡中添加内容
		tabhost = getTabHost();
		TabSpec tab1 = tabhost.newTabSpec("tab1").setIndicator(newOrder)
				.setContent(R.id.tab01);
		// 添加第一个tabhost
		tabhost.addTab(tab1);
		TabSpec tab2 = tabhost.newTabSpec("tab2").setIndicator(dealOrder)
				.setContent(R.id.tab02);
		// 添加第二个tabhost
		tabhost.addTab(tab2);	
		
		tabhost.setOnTabChangedListener(new OnTabChangeListener(){
			@Override
			public void onTabChanged(String tabId) {
				// TODO 自动生成的方法存根
				if("tab2".equals(tabId)){
					tabtype =2;
					getData(Constants.USER_ORDER_ACCOMPLE);
				}else if("tab1".equals(tabId)){
					tabtype =1;
					getData(Constants.USER_ORDER_DOWNLOAD);
				}
			}			
		});		
			
		
		mTitle = (String) getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// 向侧滑菜单添加列表
		listitems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < imageid.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", imageid[i]);
			map.put("title", menu[i]);
			listitems.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, listitems,
				R.layout.activity_menuitem, new String[] { "title", "image" },
				new int[] { R.id.title, R.id.image });

		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(this);

		// 设置抽屉开关显示
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle("请选择");
				invalidateOptionsMenu(); // Call onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// 开启ActionBar上APP ICON的功能
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
	}

	private void getData(final int type) {
		new Thread() {
			public void run() {
				getNetUserOrder(type);
			}
		}.start();
	}

	/**
	 * 取得服务端未完成的用户订单信息
	 */
	protected void getNetUserOrder(final int type) {
		BmobQuery<UserOrder> query = new BmobQuery<UserOrder>();
		if (type == Constants.USER_ORDER_DOWNLOAD) {
			query.addWhereEqualTo("shop_Id", "DLNU201515");
			query.addWhereEqualTo("state", "1");
		} else {
			query.addWhereEqualTo("shop_Id", "DLNU201515");
			query.addWhereEqualTo("state", "2");
		}
		query.findObjects(this, new FindListener<UserOrder>() {

			@Override
			public void onSuccess(List<UserOrder> arg0) {
				if (arg0.size() > 0) {
					userOrder = arg0;
				}
				Message msg = new Message();
				msg.what = type;
				handler.sendMessage(msg);
			}

			@Override
			public void onError(int arg0, String arg1) {
				Message msg = new Message();
				msg.what = Constants.USER_ORDER_DOWNLOAD_ERROR;
				handler.sendMessage(msg);

			}
		});
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			order_mess_Loading.setVisibility(View.INVISIBLE);
			switch (msg.what) {
			case Constants.USER_ORDER_DOWNLOAD:

				adapter = new UserOrderAdapter();
				newmessagelist.setAdapter(adapter);
				break;
			case Constants.USER_ORDER_ACCOMPLE:
				adapter = new UserOrderAdapter();
				oldmessagelist.setAdapter(adapter);
				break;
			case Constants.USER_ORDER_DOWNLOAD_ERROR:
				break;
			default:
				break;
			}
		}
	};

	// 将操作栏的弹出按钮更改为搜索按钮
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_websearch).setVisible(!isDrawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 将ActionBar上的图标与Drawer结合起来
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_websearch:
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri uri = Uri.parse("http://www.baidu.com");
			intent.setData(uri);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// 设置操作栏图标
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// 需要将ActionDrawerToggle与DrawerLayout的状态同步
		// 将ActionBarDrawerToggle中的drawer图标，设置为ActionBar中的Home-Button的Icon
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			Intent intent1 = new Intent(HotelMainActivity.this,
					MenuManagementActivity.class);
			startActivity(intent1);
			break;		
		case 1:
			Intent intent2 = new Intent(HotelMainActivity.this, MyMessage.class);
			startActivity(intent2);
			break;
		case 2:
			Intent intent3 = new Intent(HotelMainActivity.this, Setting.class);
			startActivity(intent3);
			break;
		case 3:
			Intent intent4 = new Intent(HotelMainActivity.this, CallUs.class);
			startActivity(intent4);
			break;
		default:
			break;
		}
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	private class UserOrderAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return userOrder.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewMyOrderHolder holder;
			View view;
			if (convertView != null) {
				view = convertView;
				holder = (ViewMyOrderHolder) convertView.getTag();
			} else {
				view = View.inflate(getApplicationContext(),
						R.layout.my_order_lis_item, null);
				holder = new ViewMyOrderHolder();
				holder.order_shop_name = (TextView) view
						.findViewById(R.id.ordercustomername);
				holder.order_deal_state = (TextView) view
						.findViewById(R.id.order_deal_state);
				holder.iv_mess_icon = (ImageView) view
						.findViewById(R.id.iv_mess_icon);
				holder.iv_mess_name = (TextView) view
						.findViewById(R.id.iv_mess_name);
				holder.iv_mess_temp = (TextView) view
						.findViewById(R.id.iv_mess_temp);
				holder.tv_mess_price = (TextView) view
						.findViewById(R.id.tv_mess_price);
				holder.by_count = (TextView) view.findViewById(R.id.by_count);
				holder.my_order_amount = (TextView) view
						.findViewById(R.id.my_order_amount);
				holder.leave_message = (TextView) view
						.findViewById(R.id.leavemessage);
				holder.my_order_time = (TextView) view
						.findViewById(R.id.my_order_time);
				holder.my_order_comment = (Button) view
						.findViewById(R.id.my_order_comment);

				view.setTag(holder);
			}

			final UserOrder orderObject = userOrder.get(position);

			// holder.order_shop_name.setText("获取中...");
			holder.order_shop_name.setText(orderObject.getUser_Id());
			getUserMessage(orderObject.getUser_Id(), holder.order_shop_name);

			LoadImage.LoadImageFile(HotelMainActivity.this,
					Constants.LOADING_SAMLL_IMAGE,
					orderObject.getMess_Icon_Url(), new GetImageBitmap() {

						@Override
						public void getImageBitmap(Bitmap bitmap,
								String cacheUrl) {
							if (DensityUtils.judgeFileSame(
									orderObject.getMess_Icon_Url(), cacheUrl)) {
								holder.iv_mess_icon
										.setBackgroundColor(Color.WHITE);
								holder.iv_mess_icon.setImageBitmap(bitmap);
							}
						}
					});

			holder.iv_mess_name.setText("获取中...");
			holder.iv_mess_temp.setText("食品类型:获取中...");
			holder.tv_mess_price.setText("单价:获取中...");
			holder.my_order_amount.setText("共计:0.00元");
			// 从服务端异步获得店家的信息
			getShopMessage(orderObject.getShop_Id(), holder.order_shop_name);
			// 从服务端异步获得菜品的信息
			getMessMessage(orderObject, holder);

			holder.my_order_time.setText(orderObject.getSubscribeTime());
			holder.by_count.setText("x" + orderObject.getCount());
			holder.leave_message.setText("      "
					+ orderObject.getLeaveMessage());

			if (orderObject.getState().equals("0")) {
				holder.order_deal_state.setText("待支付");
			} else if (orderObject.getState().equals("1")) {
				holder.order_deal_state.setText("待完成");
			} else {
				holder.order_deal_state.setText("交易完成");
			}
			if(tabtype ==1){
				holder.my_order_comment.setText("完成交易");
				holder.my_order_comment.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						accomplishOrder(position);
					}
				});
			}else{
				holder.my_order_comment.setText("删除订单");
				holder.my_order_comment.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						deleteMyOrder(position);
					}
				});
			}
			

			return view;
		}
	}

	/**
	 * 异步取得服务端的店家信息
	 * 
	 * @param shop_Id
	 * @param order_shop_name
	 */
	public void getShopMessage(final String shop_Id,
			final TextView order_shop_name) {
		BmobQuery<ShopMsgInfo> query = new BmobQuery<ShopMsgInfo>();
		query.addWhereEqualTo("shop_Id", shop_Id);
		query.findObjects(this, new FindListener<ShopMsgInfo>() {
			@Override
			public void onSuccess(List<ShopMsgInfo> object) {
				for (ShopMsgInfo shopMsg : object) {
					if (shopMsg.getShop_Id().equals(shop_Id)) {
						// order_shop_name.setText(shopMsg.getShop_Name());
						break;
					}
				}
			}

			@Override
			public void onError(int code, String msg) {
			}
		});
	}

	/**
	 * 异步取得服务端的店家信息
	 * 
	 * @param user_Id
	 * @param order_user_name
	 */
	public void getUserMessage(final String user_Id,
			final TextView order_user_name) {
		BmobQuery<UserMessageInfo> query = new BmobQuery<UserMessageInfo>();
		query.addWhereEqualTo("username", user_Id);
		query.findObjects(this, new FindListener<UserMessageInfo>() {
			@Override
			public void onSuccess(List<UserMessageInfo> object) {
				for (UserMessageInfo userMsg : object) {
					if (userMsg.getUsername().equals(user_Id)) {
						order_user_name.setText(userMsg.getNickName());
						break;
					}
				}
			}

			@Override
			public void onError(int code, String msg) {
			}
		});
	}

	/**
	 * 异步取得服务端的菜品信息
	 * 
	 * @param mess_Id
	 * @param iv_mess_name
	 * @param iv_mess_temp
	 * @param tv_mess_price
	 */
	public void getMessMessage(final UserOrder orderObject,
			final ViewMyOrderHolder holder) {

		BmobQuery<MessMenu> query = new BmobQuery<MessMenu>();
		query.addWhereEqualTo("mess_Id", orderObject.getMess_Id());
		query.findObjects(this, new FindListener<MessMenu>() {
			@Override
			public void onSuccess(List<MessMenu> object) {
				for (MessMenu messMenu : object) {
					if (messMenu.getShop_Id().equals(orderObject.getShop_Id())) {
						holder.iv_mess_name.setText(messMenu.getMess_Name());
						holder.iv_mess_temp.setText("食品类型:"
								+ NavigationContent.menuNames[Integer
										.parseInt(messMenu.getMess_Type())]);
						holder.tv_mess_price.setText("单价:"
								+ messMenu.getMess_Price() + "元");
						float temp = messMenu.getMess_Price().floatValue()
								* orderObject.getCount().floatValue();
						holder.my_order_amount.setText("共计：" + temp + "元");
						break;
					}
				}
			}

			@Override
			public void onError(int code, String msg) {
			}
		});
	}

	/**
	 * 提示用户是否确定完成交易
	 * 
	 * @param pos
	 */
	private void accomplishOrder(final int pos) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("询问");
		builder.setCancelable(false);
		builder.setMessage("你确定此次交易吗？");
		builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				yesAccomplishpOrder(userOrder.get(pos));
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}

	/**
	 * 确定完成交易
	 * 
	 * @param userOrder
	 */
	protected void yesAccomplishpOrder(final UserOrder userorder) {
		UserOrder updateOrder = new UserOrder();
		updateOrder.setState("2");
		updateOrder.update(this, userorder.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				userOrder.remove(userorder);
				adapter.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(), "交易已完成！", 0).show();
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(getApplicationContext(), "操作失败！请稍后重试！", 0)
						.show();
			}
		});
	}
	private void deleteMyOrder(final int pos) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("询问");
		builder.setCancelable(false);
		builder.setMessage("你确定要删除吗？");
		builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				deleteUserOrder(userOrder.get(pos),pos);				
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}

	/**
	 * 删除服务端的用户订单信息
	 * @param userOrder
	 */
	protected void deleteUserOrder(UserOrder userorder,final int position) {
		UserOrder deleteOrder = new UserOrder();
		deleteOrder.setObjectId(userorder.getObjectId());
		deleteOrder.delete(this, new DeleteListener() {

		    @Override
		    public void onSuccess() {
		    	userOrder.remove(position);
				adapter.notifyDataSetChanged();
		    	Toast.makeText(getApplicationContext(), "删除成功！", 0).show();
		    }

		    @Override
		    public void onFailure(int code, String msg) {
		    	Toast.makeText(getApplicationContext(), "删除失败！", 0).show();
		    }
		});
	}
}
