
package com.xk.service.impl;

import com.xk.entity.UserAction;
import com.xk.dao.UserActionDao;
import com.xk.service.UserActionService;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* ********************************************************
* @ClassName: UserActionServiceImpl
* @Description: 行为记录表
* @author 自动生成
* @date 2016-01-14 上午 10:19:50 
*******************************************************
*/
@SuppressWarnings("all")
@Service
public class UserActionServiceImpl extends BaseServiceImpl<UserAction,Integer> implements UserActionService{

	@Autowired
	private UserActionDao userActionDao;

	@Autowired
	public void setBaseDao(UserActionDao uDao) {
		super.setBaseDao(userActionDao);
	}
}

