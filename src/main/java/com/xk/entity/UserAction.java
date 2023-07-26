
package com.xk.entity;

import java.util.Date;

/**
* ********************************************************
* @ClassName: UserAction
* @Description: 行为记录表
* @author 自动生成
* @date 2016-01-14 上午 10:19:50 
*******************************************************
*/
@SuppressWarnings("all")
public class UserAction {

	private Integer id;		//null
	private Integer system_tag;		//所属平台 
	private String remark;		//备注
	private String user_code;		//用户名英文简称(登录名)
	private String add_time;		//登陆时间
	private String action_date;     //登录日期

	public String getAction_date() {
		return action_date;
	}

	public void setAction_date(String action_date) {
		this.action_date = action_date;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSystem_tag() {
		return this.system_tag;
	}

	public void setSystem_tag(Integer system_tag) {
		this.system_tag = system_tag;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUser_code() {
		return this.user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

}

