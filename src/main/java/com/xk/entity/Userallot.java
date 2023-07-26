
package com.xk.entity;

import java.util.Date;

/**
* ********************************************************
* @ClassName: Userallot
* @Description: 用户关联菜单表
* @author 自动生成
* @date 2016-01-21 下午 04:45:25 
*******************************************************
*/
@SuppressWarnings("all")
public class Userallot {

	private Integer system_tag;		//所属平台 
	private String user_code;		//编号
	private String add_date;		//添加日期yyyy-MM-dd HH:MM:ss
	private String add_user;		//添加人
	private Integer carte_no;		//菜单编号

	public Integer getSystem_tag() {
		return this.system_tag;
	}

	public void setSystem_tag(Integer system_tag) {
		this.system_tag = system_tag;
	}

	public String getUser_code() {
		return this.user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getAdd_user() {
		return this.add_user;
	}

	public void setAdd_user(String add_user) {
		this.add_user = add_user;
	}

	public Integer getCarte_no() {
		return this.carte_no;
	}

	public void setCarte_no(Integer carte_no) {
		this.carte_no = carte_no;
	}

	public String getAdd_date() {
		return add_date;
	}

	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}

}

