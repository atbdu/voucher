
package com.xk.service;

import com.xk.entity.PermissionMapping;

/**
* ********************************************************
* @ClassName: PermissionMappingService
* @Description: 用户关联按钮表
* @author 自动生成
* @date 2016-01-24 上午 10:41:13 
*******************************************************
*/
public interface PermissionMappingService extends BaseService<PermissionMapping,Integer>{

	/**
	 * ********************************************************
	 * @Title: saveButton
	 * @Description: 分配按钮权限
	 * @return void
	 * @date 2016-01-14 下午 17:02:34 
	 ********************************************************
	 */
	public void saveButton(String user_code,String permissions,String user_logo) throws Exception;
}

