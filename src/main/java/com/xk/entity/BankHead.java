
package com.xk.entity;

import java.util.Date;

/**
* ********************************************************
* @ClassName: BankHead
* @Description: null
* @author 自动生成
* @date 2016-01-26 下午 12:41:30 
*******************************************************
*/
@SuppressWarnings("all")
public class BankHead {

	private Integer id;		//总行id
	private String bank_num;		//总行编号
	private String bank_name;		//总行名称
	private Integer grade;		//总行等级 0总行  1分行

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBank_num() {
		return this.bank_num;
	}

	public void setBank_num(String bank_num) {
		this.bank_num = bank_num;
	}

	public String getBank_name() {
		return this.bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public Integer getGrade() {
		return this.grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

}

