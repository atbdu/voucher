
package com.xk.entity;

import java.util.Date;

/**
* ********************************************************
* @ClassName: PermissionMapping
* @Description: 用户关联按钮表
* @author 自动生成
* @date 2016-01-24 上午 10:41:13 
*******************************************************
*/
@SuppressWarnings("all")
public class PermissionMapping {

	private Integer system_tag;		//所属平台 
	private String user_code;		//用户名英文简称(登陆名)
	private Date add_date;		//添加日期yyyy-MM-dd HH:MM:ss
	private String add_user;		//添加人
	private Integer per_id;		//按钮编号 外键

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

	public Date getAdd_date() {
		return this.add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public String getAdd_user() {
		return this.add_user;
	}

	public void setAdd_user(String add_user) {
		this.add_user = add_user;
	}

	public Integer getPer_id() {
		return this.per_id;
	}

	public void setPer_id(Integer per_id) {
		this.per_id = per_id;
	}

}

