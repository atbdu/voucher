
package com.xk.service;

import java.util.List;
import java.util.Map;

import com.xk.entity.Carte;
import com.xk.entity.UserInfo;

/**
* ********************************************************
* @ClassName: CarteService
* @Description: 菜单
* @author 自动生成
* @date 2016-01-12 下午 09:38:34 
*******************************************************
*/
@SuppressWarnings("all")
public interface CarteService extends BaseService<Carte,Integer>{

	/**
	 * ********************************************************
	 * @Title: carteList
	 * @Description: 查询该平台的所有菜单
	 * @return String
	 * @date 2016-01-12 下午 20:38:34 
	 ********************************************************
	 */
	public List<Object> carteList(String tag_system) throws Exception;
	/**
	 * ********************************************************
	 * @Title: publicGetMenu
	 * @Description: 查询该平台的所有菜单(Ztree所用)
	 * @return String
	 * @date 2016-01-12 下午 20:38:34 
	 ********************************************************
	 */
	public List<Map<String, Object>> publicGetMenu(String tag_system) throws Exception;
	/**
	 * ********************************************************
	 * @Title: saveOrUpdate
	 * @Description: 添加或修改菜单
	 * @return int
	 * @date 2016-01-13 下午 14:38:34 
	 ********************************************************
	 */
	public int saveOrUpdate(Carte carte,Map map,UserInfo userinfo) throws Exception;
	/**
	 * ********************************************************
	 * @Title: prepare
	 * @Description: 查看菜单
	 * @return Map
	 * @date 2016-01-13 下午 15:38:34 
	 ********************************************************
	 */
	public Map<String,Object> prepare(String carteNo,String tag_system) throws Exception;
	/**
	 * ********************************************************
	 * @Title: clearCache
	 * @Description: 清除缓存
	 * @return int
	 * @date 2016-01-13 下午 20:38:34 
	 ********************************************************
	 */
	public int clearCache(String tag_system) throws Exception;
	/**
	 * ********************************************************
	 * @Title: delete
	 * @Description: 删除菜单
	 * @return int
	 * @date 2016-01-14 下午 01:55:34 
	 ********************************************************
	 */
	public Map<String,String> delete(Integer id,String tag_system) throws Exception;
	/**
	 * ********************************************************
	 * @Title: publicMenus
	 * @Description: 赋菜单权限时获取菜单
	 * @return Map
	 * @date 2016-01-22下午 01:55:34 
	 ********************************************************
	 */
	public Map<String,Object> publicMenus(String user_code,UserInfo loginUser,String user_logo) throws Exception;
}

