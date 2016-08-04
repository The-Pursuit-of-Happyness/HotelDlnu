package com.hotel.dao;

import cn.bmob.v3.BmobObject;

public class UserCommentInfo extends BmobObject{
	
	private String user_id;
	private String mess_Id;
	private String shop_Id;
	private String Comment_type;
	private String Comment_Time;
	private Integer Comment_Start;
	private String Comment_content;
	
	public UserCommentInfo(){
		
	}
	
	public UserCommentInfo(String userId,String mess_Id,String commentId,
			String type,String time,String start,String content){
		user_id = userId;
		this.mess_Id = mess_Id;
		shop_Id = commentId;
		Comment_type = type;
		Comment_Time = time;
		this.Comment_Start = Integer.parseInt(start);
		Comment_content = content;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getShop_Id() {
		return shop_Id;
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

	public String getComment_type() {
		return Comment_type;
	}

	public void setComment_type(String comment_type) {
		Comment_type = comment_type;
	}

	public String getComment_Time() {
		return Comment_Time;
	}

	public void setComment_Time(String comment_Time) {
		Comment_Time = comment_Time;
	}

	public String getComment_content() {
		return Comment_content;
	}

	public void setComment_content(String comment_content) {
		Comment_content = comment_content;
	}

	public Integer getComment_Start() {
		return Comment_Start;
	}

	public void setComment_Start(Integer comment_Start) {
		Comment_Start = comment_Start;
	}
	
}
