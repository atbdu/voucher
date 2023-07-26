
package com.xk.entity;

import java.io.Serializable;
import java.util.Date;

/**
* ********************************************************
* @ClassName: UserInfo
* @Description: 用户信息
* @author 自动生成
* @date 2016-01-12 下午 04:14:24 
*******************************************************
*/
@SuppressWarnings("all")
public class UserInfo implements Serializable{

	private Integer system_tag;		//所属平台
	private Integer status;		    //用户是否启用 0禁用 1 启用
	private String last_login_date;	//最后一次登录时间
	private String link_phone;		//联系电话
	private String user_code;		//用户名英文简称(登陆名 唯一)
	private String add_date;	        //添加日期yyyy-MM-dd HH:MM:ss
	private String last_login_ip;	//最后一次登录ip
	private String user_pass;		//用户密码
	private String cookie;		    //Cookie信息  用于用户绑定电脑
	private String company_no;		//公司编号 0 北京 1 天津 
	private String email;		    //email
	private Integer role_no;		//角色编号
	private Integer login_num;		//登录总次数
	private String real_name;		//用户真实姓名
	private String add_user;		//添加人
	private String qq;		        //qq
	private Integer role_id;		//角色id
	private Integer err_num;		//登录错误次数

	private  String update_date;	//修改时间

	private Integer deleted;	//是否删除（0、未删除 1、已删除）
	public Integer getErr_num() {
		return err_num;
	}

	public void setErr_num(Integer err_num) {
		this.err_num = err_num;
	}



	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public Integer getSystem_tag() {
		return this.system_tag;
	}

	public void setSystem_tag(Integer system_tag) {
		this.system_tag = system_tag;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLink_phone() {
		return this.link_phone;
	}

	public void setLink_phone(String link_phone) {
		this.link_phone = link_phone;
	}

	public String getUser_code() {
		return this.user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getLast_login_ip() {
		return this.last_login_ip;
	}

	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}

	public String getUser_pass() {
		return this.user_pass;
	}

	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}

	public String getCookie() {
		return this.cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getCompany_no() {
		return company_no;
	}

	public void setCompany_no(String company_no) {
		this.company_no = company_no;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRole_no() {
		return this.role_no;
	}

	public void setRole_no(Integer role_no) {
		this.role_no = role_no;
	}

	public Integer getLogin_num() {
		return this.login_num;
	}

	public void setLogin_num(Integer login_num) {
		this.login_num = login_num;
	}

	public String getReal_name() {
		return this.real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getAdd_user() {
		return this.add_user;
	}

	public void setAdd_user(String add_user) {
		this.add_user = add_user;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getLast_login_date() {
		return last_login_date;
	}

	public void setLast_login_date(String last_login_date) {
		this.last_login_date = last_login_date;
	}

	public String getAdd_date() {
		return add_date;
	}

	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
}

