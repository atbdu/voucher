
package com.xk.entity;

import java.util.Date;

/**
* ********************************************************
* @ClassName: FileType
* @Description: 文件路径保存表
* @author 自动生成
* @date 2020-04-08 下午 04:59:43 
*******************************************************
*/
@SuppressWarnings("all")
public class FileType {

	private String type_enname;		//类型英文名称
	private String synopsis;		//文件保存路径简介
	private String base_url;		//文件类型根目录
	private Integer type_id;		//保存路径id

	public String getType_enname() {
		return this.type_enname;
	}

	public void setType_enname(String type_enname) {
		this.type_enname = type_enname;
	}

	public String getSynopsis() {
		return this.synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getBase_url() {
		return this.base_url;
	}

	public void setBase_url(String base_url) {
		this.base_url = base_url;
	}

	public Integer getType_id() {
		return this.type_id;
	}

	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}

}

