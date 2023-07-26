
package com.xk.entity;

import java.util.Date;

/**
* ********************************************************
* @ClassName: Carte
* @Description: 菜单
* @author 自动生成
* @date 2016-01-12 下午 09:38:34 
*******************************************************
*/
@SuppressWarnings("all")
public class Carte {

	private String parent_carte_no;		//父级编号
	private Integer system_tag;		//所属平台 
	private String carte_intro;		//菜单介绍
	private Integer levels;		//菜单等级
	private String carte_name;		//菜单名称
	private String target;		//打开方式
	private String add_date;		//添加日期yyyy-MM-dd HH:MM:ss
	private String rel;		//REl属性
	private String add_user;		//添加人
	private String carte_no;		//菜单编号（主键）
	private String href;		//请求地址
	private Integer id;  //主键

	public String getParent_carte_no() {
		return this.parent_carte_no;
	}

	public void setParent_carte_no(String parent_carte_no) {
		this.parent_carte_no = parent_carte_no;
	}

	public Integer getSystem_tag() {
		return this.system_tag;
	}

	public void setSystem_tag(Integer system_tag) {
		this.system_tag = system_tag;
	}

	public String getCarte_intro() {
		return this.carte_intro;
	}

	public void setCarte_intro(String carte_intro) {
		this.carte_intro = carte_intro;
	}

	public Integer getLevels() {
		return this.levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}

	public String getCarte_name() {
		return this.carte_name;
	}

	public void setCarte_name(String carte_name) {
		this.carte_name = carte_name;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getAdd_date() {
		return add_date;
	}

	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}

	public String getRel() {
		return this.rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getAdd_user() {
		return this.add_user;
	}

	public void setAdd_user(String add_user) {
		this.add_user = add_user;
	}

	public String getCarte_no() {
		return this.carte_no;
	}

	public void setCarte_no(String carte_no) {
		this.carte_no = carte_no;
	}

	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}

