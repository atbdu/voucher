
package com.xk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xk.entity.UserInfo;
import com.xk.entity.Userallot;
import com.xk.controller.BaseController;
import com.xk.dao.PermissionMappingDao;
import com.xk.dao.UserInfoDao;
import com.xk.dao.UserallotDao;
import com.xk.service.UserallotService;
import com.xk.util.DateUtil;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* ********************************************************
* @ClassName: UserallotServiceImpl
* @Description: 用户关联菜单表
* @author 自动生成
* @date 2016-01-21 下午 04:45:25 
*******************************************************
*/
@SuppressWarnings("all")
@Service
public class UserallotServiceImpl extends BaseServiceImpl<Userallot,Integer> implements UserallotService{

	@Autowired
	private UserallotDao userallotDao;
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private PermissionMappingDao pmDao;

	@Autowired
	public void setBaseDao(UserallotDao uDao) {
		super.setBaseDao(userallotDao);
	}

	/**
	 * ********************************************************
	 * @Title: save
	 * @Description: 分配菜单权限
	 * @return int
	 * @date 2016-01-23 下午 10:09:32 
	 ********************************************************
	 */
	@Transactional
	public int save(String user_code, String cartes,String user_logo) throws Exception {
		int result=0;
		int system_tag=0;
		//先获取选中用户的信息
		if ("0".equals(user_logo)) {
			UserInfo userInfo=userInfoDao.getOne(user_code);
			system_tag=userInfo.getSystem_tag();
		}
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("user_code", user_code);
		map.put("add_user", BaseController.getUserCode());
		map.put("system_tag", system_tag);
		map.put("add_date", DateUtil.getDayDateStr("yyyy-MM-dd HH:mm:ss"));
		String[] carteArr=cartes.split(",");
		map.put("cartes", carteArr);
		//删除该用户已有的菜单权限
		userallotDao.delete("deleteByUser", map);
		result=userallotDao.insert("insert",map);
		return result;
	}

	/**
	 * ********************************************************
	 * @Title: list
	 * @Description: 查询该用户拥有的菜单权限
	 * @return Map
	 * @date 2016-01-23 下午 12:00:32 
	 ********************************************************
	 */
	public Map<String, Object> list(String user_code,String user_logo) throws Exception {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		int system_tag=0;
		//先获取选中用户的信息
		if ("0".equals(user_logo)) {//总后台
			UserInfo userInfo=userInfoDao.getOne(user_code);
			system_tag=userInfo.getSystem_tag();
		}
		
		//查询该用户拥有的菜单以及该菜单下的按钮
		Map<String, Object> userMap=new HashMap<String, Object>();
		userMap.put("user_code", user_code);
		userMap.put("system_tag", system_tag);
		List<Map<String, Object>> carteBtnList=userallotDao.getList("getCarteAndButtonByUserSys", userMap);
		//获取用户所拥有的按钮
		List<Map<String, Object>> btnList=pmDao.getList("getBtnByUserSys", userMap);
		Map<String,String> ckbtnMap=new HashMap<String, String>();
		for (int i = 0; i < btnList.size(); i++) {
			ckbtnMap.put(btnList.get(i).get("PER_ID")+"", btnList.get(i).get("PER_ID")+"");
		}
		resultMap.put("tag_system", system_tag);
		resultMap.put("carteBtnList", carteBtnList);
		resultMap.put("ckbtnMap", ckbtnMap);
		return resultMap;
	}
}

