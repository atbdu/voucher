
package com.xk.entity;

import java.util.Date;

/**
* ********************************************************
* @ClassName: File
* @Description: 保存文件表
* @author 自动生成
* @date 2020-04-08 下午 04:59:21 
*******************************************************
*/
@SuppressWarnings("all")
public class File {

	private String file_size;		//文件大小
	private Integer status;		//状态(0禁用 1正常 )
	private Integer file_id;		//文件id
	private String type_enname;		//文件保存类型英文名称
	private String object_id;		//文件所属对象id
	private String synopsis;		//文件中文简介
	private String style_postfix;		//后缀名
	private String add_time;		//添加时间
	private String file_name;		//文件保存英文名称

	public String getFile_size() {
		return this.file_size;
	}

	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getFile_id() {
		return this.file_id;
	}

	public void setFile_id(Integer file_id) {
		this.file_id = file_id;
	}

	public String getType_enname() {
		return this.type_enname;
	}

	public void setType_enname(String type_enname) {
		this.type_enname = type_enname;
	}

	public String getObject_id() {
		return this.object_id;
	}

	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}

	public String getSynopsis() {
		return this.synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getStyle_postfix() {
		return this.style_postfix;
	}

	public void setStyle_postfix(String style_postfix) {
		this.style_postfix = style_postfix;
	}

	public String getAdd_time() {
		return this.add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getFile_name() {
		return this.file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

}

