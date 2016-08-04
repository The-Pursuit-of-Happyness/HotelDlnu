package com.hotel.dao;

import android.os.Parcel;
import android.os.Parcelable;
import cn.bmob.v3.BmobObject;

public class UserOrder extends BmobObject implements Parcelable{
	
	private String order_id;
	private String user_Id;
	private String shop_Id;
	private String mess_Id;
	private String mess_Icon_Url;
	private String user_Icon_Url;
	private String subscribeTime;
	private Integer count;
	private String state;
	private Boolean discuss;
	private String leaveMessage;
	
	/**
	 * private String state;
	 * state = 0;   // 已经提交订单，但是还没有付款
	 * state = 1;	// 已经付款，等待取货
	 * state = 2；	// 完成所有交易 
	 */
	
	public UserOrder(){
		
	}
	
	public UserOrder(String order_id,String user_Id,String shop_Id,String mess_Id,
			String mess_Icon_Url,String user_Icon_Url,String subscribeTime,
			String count,String state,String isDiscuss,String leaveMessage){
		this.order_id = order_id;
		this.user_Id = user_Id;
		this.shop_Id = shop_Id;
		this.mess_Id = mess_Id;
		this.mess_Icon_Url = mess_Icon_Url;
		this.user_Icon_Url = user_Icon_Url;
		this.subscribeTime = subscribeTime;
		this.count = Integer.parseInt(count);
		this.state = state;
		this.discuss = Boolean.parseBoolean(isDiscuss);
		this.leaveMessage = leaveMessage;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(String user_Id) {
		this.user_Id = user_Id;
	}

	public String getShop_Id() {
		return shop_Id;
	}

	public Boolean getDiscuss() {
		return discuss;
	}

	public void setDiscuss(Boolean discuss) {
		this.discuss = discuss;
	}

	public void setShop_Id(String shop_Id) {
		this.shop_Id = shop_Id;
	}

	public String getMess_Id() {
		return mess_Id;
	}

	public void setMess_Id(String mess_Id) {
		this.mess_Id = mess_Id;
	}
	
	public String getMess_Icon_Url() {
		return mess_Icon_Url;
	}

	public void setMess_Icon_Url(String mess_Icon_Url) {
		this.mess_Icon_Url = mess_Icon_Url;
	}

	public String getUser_Icon_Url() {
		return user_Icon_Url;
	}

	public void setUser_Icon_Url(String user_Icon_Url) {
		this.user_Icon_Url = user_Icon_Url;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLeaveMessage() {
		return leaveMessage;
	}

	public void setLeaveMessage(String leaveMessage) {
		this.leaveMessage = leaveMessage;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(order_id);
		dest.writeString(user_Id);
		dest.writeString(shop_Id);
		dest.writeString(mess_Id);
		dest.writeString(mess_Icon_Url);
		dest.writeString(user_Icon_Url);
		dest.writeString(subscribeTime);
		dest.writeString(String.valueOf(count));
		dest.writeString(String.valueOf(state));
		dest.writeString(String.valueOf(discuss));
		dest.writeString(leaveMessage);
		
	}
	
	public static final Parcelable.Creator<UserOrder> CREATOR = new UserOrder.Creator<UserOrder>() {
		@Override
		public UserOrder createFromParcel(Parcel source) {
			// 从Parcel中读取数据
			// 此处read顺序依据write顺序
			return new UserOrder(source.readString(),
					source.readString(), source.readString(),
					source.readString(), source.readString(),
					source.readString(), source.readString(),
					source.readString(), source.readString(),
					source.readString(),source.readString());
		}

		@Override
		public UserOrder[] newArray(int size) {
			return new UserOrder[size];
		}
	};
	
}
