package com.hotel.dao;

import cn.bmob.v3.BmobUser;

public class UserMessageInfo extends BmobUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nickName;
	private String graphUrl;
	private String sex;
	private String address;
	private String money;
	private String major;
	private String autograph;

	public UserMessageInfo() {

	}

	public UserMessageInfo(String nickName, String graph, String money,
			String sex, String address, String major, String autograph) {
		this.nickName = nickName;
		this.graphUrl = graph;
		this.money = money;
		this.sex = sex;
		this.address = address;
		this.major = major;
		this.autograph = autograph;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGraph() {
		return graphUrl;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public void setGraph(String graph) {
		this.graphUrl = graph;
	}

	public String getGraphUrl() {
		return graphUrl;
	}

	public void setGraphUrl(String graphUrl) {
		this.graphUrl = graphUrl;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public String getMoney() {
		return money;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
}
