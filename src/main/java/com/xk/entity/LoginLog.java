
package com.xk.entity;

import java.util.Date;

/**
* ********************************************************
* @ClassName: LoginLog
* @Description: 登录日志表
* @author 自动生成
* @date 2016-01-13 下午 06:48:10 
*******************************************************
*/
@SuppressWarnings("all")
public class LoginLog {

	private Integer id;		//null
	private String remark;		//备注
	private Integer status;		//登陆状态 0 未成功  1成功
	private String user_code;		//用户名英文简称(登录名)
	private String add_date;		//添加日期yyyy-MM-dd 
	private String add_time;		//登陆时间HH:MM:ss
	private String login_ip;		//登录ip
	private Integer system_tag;		//所属平台 0-总后台、1-财务、2-代理商、3-商户

	private String max_date;//最大日期
	private String min_date;//最小日期
	private String max_time;//最大时间
	private String min_time;//最小时间
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUser_code() {
		return this.user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getLogin_ip() {
		return this.login_ip;
	}

	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}

	public String getAdd_date() {
		return add_date;
	}

	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public Integer getSystem_tag() {
		return system_tag;
	}

	public void setSystem_tag(Integer system_tag) {
		this.system_tag = system_tag;
	}

	public String getMax_date() {
		return max_date;
	}

	public void setMax_date(String max_date) {
		this.max_date = max_date;
	}

	public String getMin_date() {
		return min_date;
	}

	public void setMin_date(String min_date) {
		this.min_date = min_date;
	}

	public String getMax_time() {
		return max_time;
	}

	public void setMax_time(String max_time) {
		this.max_time = max_time;
	}

	public String getMin_time() {
		return min_time;
	}

	public void setMin_time(String min_time) {
		this.min_time = min_time;
	}

}

