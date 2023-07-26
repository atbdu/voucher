
package com.xk.entity;

import java.util.Date;

/**
* ********************************************************
* @ClassName: BaseInfoClass
* @Description: 基本信息表
* @author 自动生成
* @date 2016-01-12 下午 08:46:09 
*******************************************************
*/
@SuppressWarnings("all")
public class BaseInfoClass {

	private Integer status;		//是否启用 0 未启用 1启用 2禁用
	private String ramark;		//备注
	private String add_date;		//添加日期yyyy-MM-dd HH:MM:ss
	private String class_en;		//主键（唯一）英文名称
	private String class_cn;		//中文名称
	private String add_user;		//添加人
	
	private String real_name;//添加人-中文名称

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRamark() {
		return this.ramark;
	}

	public void setRamark(String ramark) {
		this.ramark = ramark;
	}

	public String getAdd_date() {
		return add_date;
	}

	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}

	public String getClass_en() {
		return this.class_en;
	}

	public void setClass_en(String class_en) {
		this.class_en = class_en;
	}

	public String getClass_cn() {
		return this.class_cn;
	}

	public void setClass_cn(String class_cn) {
		this.class_cn = class_cn;
	}

	public String getAdd_user() {
		return this.add_user;
	}

	public void setAdd_user(String add_user) {
		this.add_user = add_user;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

}

