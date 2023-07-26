
package com.xk.entity;

import java.util.Date;

/**
* ********************************************************
* @ClassName: BaseInfo
* @Description: 基本详细信息表
* @author 自动生成
* @date 2016-01-12 下午 08:45:56 
*******************************************************
*/
@SuppressWarnings("all")
public class BaseInfo {

	private Integer id;		//主键（唯一）
	private Integer status;		//是否启用 0 未启用 1启用 2禁用
	private String add_date;		//添加日期yyyy-MM-dd HH:MM:ss
	private String class_en;		//所属控件英文
	private String add_user;		//添加人
	private String info_en;		//信息标识
	private String info;		//信息值
	
	private String real_name;//添加人-中文名称

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getAdd_user() {
		return this.add_user;
	}

	public void setAdd_user(String add_user) {
		this.add_user = add_user;
	}

	public String getInfo_en() {
		return this.info_en;
	}

	public void setInfo_en(String info_en) {
		this.info_en = info_en;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

}

