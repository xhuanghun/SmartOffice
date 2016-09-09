package com.example.bean;

import com.example.smartoffice.R.id;

public class LightMsgItem {

	private int id;
	private String name;
	private String time;
	private int imgid;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getImgid() {
		return imgid;
	}

	public void setImgid(int imgid) {
		this.imgid = imgid;
	}

	public LightMsgItem(int id, String name, String time, int imgid) {
		super();
		this.id = id;
		this.name = name;
		this.time = time;
		this.imgid = imgid;
	}

	
}
