
package com.xk.service.impl;

import com.xk.entity.LoginLog;
import com.xk.dao.LoginLogDao;
import com.xk.service.LoginLogService;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* ********************************************************
* @ClassName: LoginLogServiceImpl
* @Description: 登录日志表
* @author 自动生成
* @date 2016-01-13 下午 06:48:10 
*******************************************************
*/
@SuppressWarnings("all")
@Service
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLog,Integer> implements LoginLogService{

	@Autowired
	private LoginLogDao loginLogDao;

	@Autowired
	public void setBaseDao(LoginLogDao lDao) {
		super.setBaseDao(loginLogDao);
	}
}

