package com.hotel.dao;

import android.os.Parcel;
import android.os.Parcelable;
import cn.bmob.v3.BmobObject;

public class ShopMsgInfo extends BmobObject implements Parcelable {

	private String shop_Id;
	private String logoUrl;
	private String shop_Name;
	private String shop_Call;
	private String shop_Address;
	private Integer shop_visit;
	private Boolean isPopular;
	private Integer shop_Star;
	private String shop_Describe;
	private String server_Area;
	private String server_Purpose;

	public ShopMsgInfo() {

	}

	public ShopMsgInfo(String shop_Id,String logo, String shop_Name, String shop_Relation,
			String shop_Address,String shop_visit, String isPopular, String shop_Star,
			String shop_Describe,String server_Area,String server_Purpose) {

		this.shop_Id = shop_Id;
		this.logoUrl = logo;
		this.shop_Name = shop_Name;
		this.shop_Call = shop_Relation;
		this.shop_Address = shop_Address;
		this.shop_visit = Integer.parseInt(shop_visit);
		this.isPopular = Boolean.parseBoolean(isPopular);
		this.shop_Star = Integer.parseInt(shop_Star);
		this.shop_Describe = shop_Describe;
		this.server_Area = server_Area;
		this.server_Purpose = server_Purpose;
	}

	public String getShop_Id() {
		return shop_Id;
	}

	public String getLogo() {
		return logoUrl;
	}

	public void setLogo(String logo) {
		this.logoUrl = logo;
	}

	public void setShop_Id(String shop_Id) {
		this.shop_Id = shop_Id;
	}

	public String getShop_Name() {
		return shop_Name;
	}

	public void setShop_Name(String shop_Name) {
		this.shop_Name = shop_Name;
	}

	public String getShop_Call() {
		return shop_Call;
	}

	public void setShop_Call(String shop_Call) {
		this.shop_Call = shop_Call;
	}

	public String getShop_Address() {
		return shop_Address;
	}

	public void setShop_Address(String shop_Address) {
		this.shop_Address = shop_Address;
	}
	
	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Integer getShop_visit() {
		return shop_visit;
	}

	public void setShop_visit(Integer shop_visit) {
		this.shop_visit = shop_visit;
	}

	public Boolean getIsPopular() {
		return isPopular;
	}

	public void setIsPopular(Boolean isPopular) {
		this.isPopular = isPopular;
	}

	public Integer getShop_Star() {
		return shop_Star;
	}

	public void setShop_Star(Integer shop_Star) {
		this.shop_Star = shop_Star;
	}

	public String getShop_Describe() {
		return shop_Describe;
	}

	public void setShop_Describe(String shop_Describe) {
		this.shop_Describe = shop_Describe;
	}

	public String getServer_Area() {
		return server_Area;
	}

	public void setServer_Area(String server_Area) {
		this.server_Area = server_Area;
	}

	public String getServer_Purpose() {
		return server_Purpose;
	}

	public void setServer_Purpose(String server_Purpose) {
		this.server_Purpose = server_Purpose;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(shop_Id);
		dest.writeString(logoUrl);
		dest.writeString(shop_Name);
		dest.writeString(shop_Call);
		dest.writeString(shop_Address);
		dest.writeString(String.valueOf(shop_visit));
		dest.writeString(String.valueOf(isPopular));
		dest.writeString(String.valueOf(shop_Star));
		dest.writeString(shop_Describe);
		dest.writeString(server_Area);
		dest.writeString(server_Purpose);
	}

	/**
	 * Ω‚ ÕÕ¨OrderMessMsgInfo
	 */
	public static final Parcelable.Creator<ShopMsgInfo> CREATOR = new ShopMsgInfo.Creator<ShopMsgInfo>() {
		@Override
		public ShopMsgInfo createFromParcel(Parcel source) {
			return new ShopMsgInfo(source.readString(), 
					source.readString(),source.readString(),
					source.readString(), source.readString(),
					source.readString(), source.readString(),
					source.readString(),source.readString(),
					source.readString(),source.readString());
		}

		@Override
		public ShopMsgInfo[] newArray(int size) {
			return new ShopMsgInfo[size];
		}
	};

}
