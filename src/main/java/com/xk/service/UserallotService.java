
package com.xk.service;

import java.util.Map;

import com.xk.entity.Userallot;

/**
* ********************************************************
* @ClassName: UserallotService
* @Description: 用户关联菜单表
* @author 自动生成
* @date 2016-01-21 下午 04:45:25 
*******************************************************
*/
public interface UserallotService extends BaseService<Userallot,Integer>{

	/**
	 * ********************************************************
	 * @Title: save
	 * @Description: 分配菜单权限
	 * @return int
	 * @date 2016-01-23 下午 10:09:32 
	 ********************************************************
	 */
	public int save(String user_code,String cartes,String user_logo) throws Exception;
	/**
	 * ********************************************************
	 * @Title: list
	 * @Description: 查询该用户拥有的菜单权限
	 * @return Map
	 * @date 2016-01-23 下午 12:00:32 
	 ********************************************************
	 */
	public Map<String,Object> list(String user_code,String user_logo) throws Exception;
}

