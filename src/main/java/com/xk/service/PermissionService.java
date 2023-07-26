
package com.xk.service;

import java.util.List;
import java.util.Map;

import com.xk.entity.Permission;
import com.xk.entity.UserInfo;

/**
* ********************************************************
* @ClassName: PermissionService
* @Description: 按钮
* @author 自动生成
* @date 2016-01-14 上午 10:32:53 
*******************************************************
*/
public interface PermissionService extends BaseService<Permission,Integer>{

	/**
	 * ********************************************************
	 * @Title: getBtnByCarte
	 * @Description: 获取菜单下的所有按钮
	 * @return List<Map<String, Object>>
	 * @date 2016-01-14 下午 15:02:34 
	 ********************************************************
	 */
	public List<Map<String, Object>> getBtnByCarte(Permission permission) throws Exception;
	/**
	 * ********************************************************
	 * @Title: saveOrUpdate
	 * @Description: 添加或修改按钮
	 * @return int
	 * @date 2016-01-14 下午 16:02:34 
	 ********************************************************
	 */
	public int saveOrUpdate(Permission permission,UserInfo userinfo) throws Exception;
	/**
	 * ********************************************************
	 * @Title: delete
	 * @Description: 删除
	 * @return int
	 * @date 2016-01-14 下午 17:02:34 
	 ********************************************************
	 */
	public int delete(Permission permission) throws Exception;
	/**
	 * ********************************************************
	 * @Title: getBtnShow
	 * @Description: 检测按钮是否显示
	 * @return boolean
	 * @date 2016-01-26 下午 17:02:34 
	 ********************************************************
	 */
	public boolean getBtnShow(String carte_id,String href,String buttonname,UserInfo userInfo) throws Exception;
}

