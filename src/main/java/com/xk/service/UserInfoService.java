
package com.xk.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xk.entity.UserInfo;

/**
* ********************************************************
* @ClassName: UserInfoService
* @Description: 用户信息
* @author 自动生成
* @date 2016-01-12 下午 04:14:24 
*******************************************************
*/
public interface UserInfoService extends BaseService<UserInfo,Integer>{
    //用户登陆
	public String loginUser(Map<String,String> map, HttpServletRequest request) throws Exception;
	/**
	 * ********************************************************
	 * @Title: saveUserinfo
	 * @Description: 添加
	 * @return String
	 * @date 2016-01-12 下午 04:34:32 
	 ********************************************************
	 */
	public boolean saveUserinfo(String loginUser_code,UserInfo userInfo) throws Exception;
	/**
	 * ********************************************************
	 * @Title: deleteUser_info
	 * @Description: 删除
	 * @return Integer
	 * @date 2016-01-12 下午 04:34:32 
	 ********************************************************
	 */
	public Integer deleteUser_info(String loginUser_code,String user_code) throws Exception;
	/**
	 *********************************************************  
	 * @Title: updateUserinfo
	 * @Description: 更新用户 
	 * @return String
	 * @date 2016-1-14
	 ********************************************************
	 */
	public boolean updateUserinfo(UserInfo userInfo) throws Exception;
	/**
	  *********************************************************  
	  * @Title: updateStatus
	  * @Description: 修改状态 0禁用  1启用  
	  * @return Map
	  * @date 2016-1-14
	  ********************************************************
	  */
	public boolean updateStatus(String loginUser_code,String user_code) throws Exception;
	/**
	  *********************************************************  
	  * @Title: updatepass
	  * @Description: 重置密码
	  * @return Map
	  * @date 2016-1-26
	  ********************************************************
	  */
	public int updatepass(String user_code,String pwd) throws Exception;
	
	
}


