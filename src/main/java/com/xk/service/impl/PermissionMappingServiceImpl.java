
package com.xk.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.xk.entity.PermissionMapping;
import com.xk.entity.UserInfo;
import com.xk.controller.BaseController;
import com.xk.dao.PermissionMappingDao;
import com.xk.dao.UserInfoDao;
import com.xk.service.PermissionMappingService;
import com.xk.util.DateUtil;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* ********************************************************
* @ClassName: PermissionMappingServiceImpl
* @Description: 用户关联按钮表
* @author 自动生成
* @date 2016-01-24 上午 10:41:13 
*******************************************************
*/
@SuppressWarnings("all")
@Service
public class PermissionMappingServiceImpl extends BaseServiceImpl<PermissionMapping,Integer> implements PermissionMappingService{

	@Autowired
	private PermissionMappingDao permissionMappingDao;
	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	public void setBaseDao(PermissionMappingDao pDao) {
		super.setBaseDao(permissionMappingDao);
	}

	/**
	 * ********************************************************
	 * @Title: saveButton
	 * @Description: 分配按钮权限
	 * @return void
	 * @date 2016-01-14 下午 17:02:34 
	 ********************************************************
	 */
	@Transactional
	public void saveButton(String user_code, String permissions,String user_logo)
			throws Exception {
		int system_tag=0;
		//先获取选中用户的信息
		if ("0".equals(user_logo)) {
			UserInfo userInfo=userInfoDao.getOne(user_code);
			system_tag=userInfo.getSystem_tag();
		}
		Map<String,Object> userMap=new HashMap<String, Object>();
		userMap.put("user_code", user_code);
		userMap.put("system_tag", system_tag);
		//先删除该用户已拥有的按钮(加上平台标识)
		permissionMappingDao.delete("deleteBtnByUserAndSys",userMap);
		//添加按钮权限
		if (permissions!=null && !"".equals(permissions)) {
			String[] perArr=permissions.split(",");
			userMap.put("add_user", BaseController.getUserCode());
			userMap.put("add_date", DateUtil.getDayDateStr("yyyy-MM-dd HH:mm:ss"));
			userMap.put("perArr", perArr);
			permissionMappingDao.insert("insert", userMap);
		}
	}
}

