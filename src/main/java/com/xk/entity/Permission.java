
package com.xk.entity;

import java.util.Date;

/**
* ********************************************************
* @ClassName: Permission
* @Description: 按钮
* @author 自动生成
* @date 2016-01-14 上午 10:32:53 
*******************************************************
*/
@SuppressWarnings("all")
public class Permission {

	private Integer system_tag;		//所属平台
	private String lower_href;		//子窗体表单地址
	private Integer carte_id;		//菜单编号
	private String add_date;		//添加日期yyyy-MM-dd HH:MM:ss
	private String button_name;		//按钮名称
	private String add_user;		//添加人
	private Integer per_id;		//编号(主键)
	private String href;		//按钮请求路径

	public Integer getSystem_tag() {
		return this.system_tag;
	}

	public void setSystem_tag(Integer system_tag) {
		this.system_tag = system_tag;
	}

	public String getLower_href() {
		return this.lower_href;
	}

	public void setLower_href(String lower_href) {
		this.lower_href = lower_href;
	}

	public Integer getCarte_id() {
		return this.carte_id;
	}

	public void setCarte_id(Integer carte_id) {
		this.carte_id = carte_id;
	}


	public String getButton_name() {
		return this.button_name;
	}

	public void setButton_name(String button_name) {
		this.button_name = button_name;
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

	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getAdd_date() {
		return add_date;
	}

	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}

}

