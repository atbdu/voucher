
package com.xk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xk.entity.Permission;
import com.xk.entity.UserInfo;
import com.xk.dao.PermissionDao;
import com.xk.service.PermissionService;
import com.xk.util.DateUtil;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* ********************************************************
* @ClassName: PermissionServiceImpl
* @Description: 按钮
* @author 自动生成
* @date 2016-01-14 上午 10:32:53 
*******************************************************
*/
@SuppressWarnings("all")
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission,Integer> implements PermissionService{

	@Autowired
	private PermissionDao permissionDao;

	@Autowired
	public void setBaseDao(PermissionDao pDao) {
		super.setBaseDao(permissionDao);
	}

	/**
	 * ********************************************************
	 * @Title: getBtnByCarte
	 * @Description: 获取菜单下的所有按钮
	 * @return List<Map<String, Object>>
	 * @date 2016-01-14 下午 15:02:34 
	 ********************************************************
	 */
	public List<Map<String, Object>> getBtnByCarte(Permission permission) throws Exception {
		List<Map<String, Object>> btnList=new ArrayList<Map<String,Object>>();
		try {
			btnList=permissionDao.getList("getAllButtonByCarte", permission);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			
		}
		return btnList;
	}

	/**
	 * ********************************************************
	 * @Title: saveOrUpdate
	 * @Description: 添加或修改按钮
	 * @return int
	 * @date 2016-01-14 下午 16:02:34 
	 ********************************************************
	 */
	@Transactional
	public int saveOrUpdate(Permission permission,UserInfo userinfo)
			throws Exception {
		int result=0;
		try {
			permission.setAdd_user(userinfo.getUser_code());
			permission.setAdd_date(DateUtil.getDateFormate(DateUtil.getDayDate("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
			if (permission.getPer_id()==null) {
				//添加
				result=permissionDao.insert(permission);
			}else{
				//修改
				result=permissionDao.update(permission);
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * ********************************************************
	 * @Title: delete
	 * @Description: 删除
	 * @return int
	 * @date 2016-01-14 下午 17:02:34 
	 ********************************************************
	 */
	@Transactional
	public int delete(Permission permission) throws Exception {
		int result=0;
		try {
			result=permissionDao.delete("delete", permission);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * ********************************************************
	 * @Title: getBtnShow
	 * @Description: 检测按钮是否显示
	 * @return boolean
	 * @date 2016-01-26 下午 17:02:34 
	 ********************************************************
	 */
	public boolean getBtnShow(String carte_id, String href, String buttonname,UserInfo userInfo)
			throws Exception {
		boolean result=false;
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("system_tag", userInfo.getSystem_tag());
		map.put("user_code", userInfo.getUser_code());
		List<Map<String, Object>> perList=(List<Map<String, Object>>) memCache.get("userBtn"+userInfo.getUser_code());
		if (perList==null||perList.size()==0) {
			perList=permissionDao.getList("getAllButtonByUser", map);
			memCache.add("userBtn"+userInfo.getUser_code(), perList);
		}
		for (int i = 0; i < perList.size(); i++) {
			Map<String,Object> tmp=perList.get(i);
			if (carte_id.equals(tmp.get("CARTE_ID")+"")) {
				String hrefTmp=href.replace("_", "/")+"/";
				if (hrefTmp.equals(tmp.get("HREF")+"")) {
					//if (buttonname.equals(tmp.get("BUTTON_NAME")+"")) {
						result=true;
						break;
					//}
				}
			}
		}
		
		return result;
	}
}

