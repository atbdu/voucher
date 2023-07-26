
package com.xk.entity;

/**
* ********************************************************
* @ClassName: SystemNotification
* @Description: 公告表
* @author 自动生成
* @date 2021-08-10 上午 03:39:40 
*******************************************************
*/
@SuppressWarnings("all")
public class SystemNotification {

	private String end_date;		//结束日期
	private Integer id;		//
	private String body;		//公告内容
	private String start_date;		//开始日期

	public String getEnd_date() {
		return this.end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getStart_date() {
		return this.start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	@Override
	public String toString() {
		return "SystemNotification{" +
				"end_date='" + end_date + '\'' +
				", id=" + id +
				", body='" + body + '\'' +
				", start_date='" + start_date + '\'' +
				'}';
	}
}

