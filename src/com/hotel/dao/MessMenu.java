package com.hotel.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/*
 * create table messMenu(mess_id varchar(20)," +
				"shop_id varchar(20),mess_name varchar(40),mess_price " +
				"smallint,mess_star varchar(5),sales_volume smallint,marketTime DATE," +
				"mess_Type varchar(5),isPuplar varchar(4),mess_describe " +
				"varchar2,messFeature varchar2," +
				"messEffect varchar2,primary key(mess_id)
 */
public class MessMenu extends BmobObject implements Parcelable{
	
	private String mess_Id;
	private String shop_Id;
	private String mess_Name;
	private String mess_Graph;
	private Integer mess_Price;
	private Integer mess_Start;
	private Integer sales_Volume;
	private BmobDate market_Time;
	private String mess_Type;
	private Boolean isPuplar;
	private String mess_Describe;
	private String mess_Feature;
	private String mess_Effect;
	
	/**
	 * 无参构造函数
	 */
	public MessMenu(){

	}
	/**
	 * 有参构造函数
	 */
	public MessMenu(String mess_Id,String shop_Id,String mess_Name,
			String mess_Graph,String mess_Price,String mess_Start,
			String sales_Volume,String date,String messType,String puplar,
			String messDescribe,String messFeature,String messEffect) {
		
		this.mess_Id = mess_Id;
		this.shop_Id = shop_Id;
		this.mess_Name = mess_Name;
		this.mess_Graph = mess_Graph;
		this.mess_Price = Integer.parseInt(mess_Price);
		this.mess_Start = Integer.parseInt(mess_Start);
		this.sales_Volume = Integer.parseInt(sales_Volume);
		try {
			this.market_Time = new BmobDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.mess_Type = messType;
		boolean temp = false;
		if(puplar.equals("true")){
			temp = true;
		}
		this.isPuplar = temp;
		this.mess_Describe = messDescribe;
		this.mess_Feature = messFeature;
		this.mess_Effect = messEffect;
	}
	
	public String getMess_Id() {
		return mess_Id;
	}
	public void setMess_Id(String mess_Id) {
		this.mess_Id = mess_Id;
	}
	public String getShop_Id() {
		return shop_Id;
	}
	public void setShop_Id(String shop_Id) {
		this.shop_Id = shop_Id;
	}
	public String getMess_Name() {
		return mess_Name;
	}
	public void setMess_Name(String mess_Name) {
		this.mess_Name = mess_Name;
	}
	public String getMess_Graph() {
		return mess_Graph;
	}
	public void setMess_Graph(String mess_Graph) {
		this.mess_Graph = mess_Graph;
	}
	public Integer getMess_Price() {
		return mess_Price;
	}
	public void setMess_Price(Integer mess_Price) {
		this.mess_Price = mess_Price;
	}
	public Integer getMess_Start() {
		return mess_Start;
	}
	public void setMess_Start(Integer mess_Start) {
		this.mess_Start = mess_Start;
	}
	public Integer getSales_Volume() {
		return sales_Volume;
	}
	public void setSales_Volume(Integer sales_Volume) {
		this.sales_Volume = sales_Volume;
	}
	
	public BmobDate getMarket_Time() {
		return market_Time;
	}
	public void setMarket_Time(BmobDate market_Time) {
		this.market_Time = market_Time;
	}
	public String getMess_Type() {
		return mess_Type;
	}
	public void setMess_Type(String mess_Type) {
		this.mess_Type = mess_Type;
	}
	public Boolean getIsPuplar() {
		return isPuplar;
	}
	public void setIsPuplar(Boolean isPuplar) {
		this.isPuplar = isPuplar;
	}
	public String getMess_Describe() {
		return mess_Describe;
	}
	public void setMess_Describe(String mess_Describe) {
		this.mess_Describe = mess_Describe;
	}
	
	public String getMess_Feature() {
		return mess_Feature;
	}
	public void setMess_Feature(String mess_Feature) {
		this.mess_Feature = mess_Feature;
	}
	public String getMess_Effect() {
		return mess_Effect;
	}
	public void setMess_Effect(String mess_Effect) {
		this.mess_Effect = mess_Effect;
	}
	
	// 实现parcelable接口，以实现intent传递我们自定义的数据对象
		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(mess_Id);
			dest.writeString(shop_Id);
			dest.writeString(mess_Name);
			dest.writeString(mess_Graph);
			dest.writeString(String.valueOf(mess_Price));
			dest.writeString(String.valueOf(mess_Start));
			dest.writeString(String.valueOf(sales_Volume));
			dest.writeString(String.valueOf(market_Time));
			dest.writeString(mess_Type);
			dest.writeString(String.valueOf(isPuplar));
			dest.writeString(mess_Describe);
			dest.writeString(mess_Feature);
			dest.writeString(mess_Effect);
		}

		// 3、自定义类型中必须含有一个名称为CREATOR的静态成员，
		// 该成员对象要求实现Parcelable.Creator接口及其方法
		public static final Parcelable.Creator<MessMenu> CREATOR = new MessMenu.Creator<MessMenu>() {
			@Override
			public MessMenu createFromParcel(Parcel source) {
				// 从Parcel中读取数据
				// 此处read顺序依据write顺序
				return new MessMenu(source.readString(),
						source.readString(), source.readString(),
						source.readString(), source.readString(),
						source.readString(), source.readString(),
						source.readString(),source.readString(),
						source.readString(),source.readString(),
						source.readString(),source.readString());
			}

			@Override
			public MessMenu[] newArray(int size) {
				return new MessMenu[size];
			}
		};
}
