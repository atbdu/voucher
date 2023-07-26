
package com.xk.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xk.entity.Carte;
import com.xk.entity.UserInfo;
import com.xk.dao.CarteDao;
import com.xk.dao.PermissionDao;
import com.xk.service.CarteService;
import com.xk.service.UserInfoService;
import com.xk.util.CommonUtil;
import com.xk.util.DateUtil;
import com.xk.util.Utils;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* ********************************************************
* @ClassName: CarteServiceImpl
* @Description: 菜单
* @author 自动生成
* @date 2016-01-12 下午 09:38:34 
*******************************************************
*/
@SuppressWarnings("all")
@Service
public class CarteServiceImpl extends BaseServiceImpl<Carte,Integer> implements CarteService{

	@Autowired
	private CarteDao carteDao;
	@Autowired
	private PermissionDao permissionDao;//按钮
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	public void setBaseDao(CarteDao cDao) {
		super.setBaseDao(carteDao);
	}

	/**
	 * ********************************************************
	 * @Title: carteList
	 * @Description: 查询该平台的所有菜单
	 * @return String
	 * @date 2016-01-12 下午 09:38:34 
	 ********************************************************
	 */
	public List<Object> carteList(String tag_system) throws Exception{
		//获取缓存中改平台的所有菜单
		List<Object> carteList=new ArrayList<Object>();
		try {
			Object obj=memCache.get("carteCache"+tag_system);
			if (obj==null) {
				//从缓存中没有获取到菜单则查询数据库，并且放进缓存中
				List<Map<String, Object>> cartes=carteDao.getList("getAllCarteByTag", Integer.parseInt(tag_system));
				Map carteMap=CommonUtil.listToMap(cartes,"CARTE_NO");
				//放进缓存
				memCache.add("carteList"+tag_system, cartes);
				memCache.add("carteCache"+tag_system, carteMap);
				obj=memCache.get("carteCache"+tag_system);
			}
			if (obj!=null) {
				Map<String, Object> carteMap=(Map<String, Object>)obj;
				carteList=CommonUtil.mapToList(carteMap);
			}
		} catch (Exception e) {
			throw e;
		}finally{
			
		}
		return carteList;
	}

	/**
	 * ********************************************************
	 * @Title: publicGetMenu
	 * @Description: 查询该平台的所有菜单(Ztree所用)
	 * @return String
	 * @date 2016-01-12 下午 09:38:34 
	 ********************************************************
	 */
	public List<Map<String, Object>> publicGetMenu(String tag_system)
			throws Exception {
		List<Map<String, Object>> carteList = null;
		Map<String, String> treeKey = null;
		try {
			//先从缓存中获取菜单，没获取到就查询数据库获取
			carteList=(List<Map<String, Object>>)memCache.get("carteList"+tag_system);
			if (carteList==null || carteList.size()==0) {
				carteList=carteDao.getList("getAllCarteByTag", Integer.parseInt(tag_system));
			}
			treeKey=new HashMap<String, String>();
			treeKey.put("id", "CARTE_NO");
			treeKey.put("name", "CARTE_NAME");
			treeKey.put("pId", "PARENT_CARTE_NO");
			carteList=Utils.toZtreeList(carteList, treeKey);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			
		}
		return carteList;
	}

	/**
	 * ********************************************************
	 * @Title: saveOrUpdate
	 * @Description: 添加或修改菜单
	 * @return int
	 * @date 2016-01-13 下午 14:38:34 
	 ********************************************************
	 */
	@Transactional
	public int saveOrUpdate(Carte carte, Map map,UserInfo userinfo) throws Exception {
		int result=0;
		int resultFlag=0;//默认 0-成功
		//当前选中的菜单编号
		String curent_seletedno=(String)map.get("organ_nb");
		//父类菜单编号
		String parentNo=(String)map.get("parentNo");
		//原菜单编号
		String curent_no=(String)map.get("oldCarteNo");
		//操作标识 0-添加、1-修改
		String operateLogo=(String)map.get("operateLogo");
		//默认级别
		int levels=1;
		//平台标识
		int tag_system=Integer.parseInt(map.get("tag_system")+"");
		/**
		 * 判断是否是根节点，如果不是，则需要根据选中的菜单判断添加的菜单属于几级菜单，
		 * 并且levels会随着变动，parent_no也会随之改变
		 */
		if ("0".equals(curent_seletedno)) {
			//根节点(获取该菜单下最大的菜单编号)
			String maxCarteNo=(String)carteDao.getObject("getMaxCarteNo",tag_system);
			curent_no=(maxCarteNo!=null?maxCarteNo:"00000000");
			curent_no=getCarteNo(curent_no, levels);
		}else{
			//不是根节点，根据选择的父菜单获取当前菜单的级别
			levels=getLevels(curent_seletedno)+1;
			//判断当前选择的父菜单是否是原父菜单，不相同才需要进行下一步操作
			if (!curent_seletedno.equals(parentNo)) {
				//判断是添加还是修改
				if ("0".equals(operateLogo)) {
					Map tmp=new HashMap();
					tmp.put("system_tag", tag_system);
					tmp.put("parent_carte_no", curent_seletedno);
					String maxCarteNo=(String)carteDao.getObject("getMaxCarteNoByNo", tmp);
					if (maxCarteNo==null) {
						//没有节点
						curent_no=getCarteNo(curent_seletedno, levels);
					}else{
						curent_no=getCarteNo(maxCarteNo, levels);
					}
				}else{//修改
					Map tmp=new HashMap();
					tmp.put("system_tag", tag_system);
					tmp.put("parent_carte_no", curent_no);
					String maxCarteNo=(String)carteDao.getObject("getMaxCarteNoByNo", tmp);
					if (maxCarteNo==null) {
						//查询选中菜单下的最大节点
						tmp.put("parent_carte_no", curent_seletedno);
						maxCarteNo=(String)carteDao.getObject("getMaxCarteNoByNo", tmp);
						//没有节点
						curent_no=getCarteNo(maxCarteNo, levels);
					}else{
						resultFlag=1;
					}
				}
			}
		}
		//resultFlag==0 表示成功，可以继续下一步操作
		if (resultFlag==0) {
			carte.setCarte_no(curent_no);
			carte.setLevels(levels);
			carte.setSystem_tag(tag_system);
			carte.setAdd_user(userinfo.getUser_code());
			carte.setAdd_date(DateUtil.getDayDateStr("yyyy-MM-dd HH:mm:ss"));
			carte.setParent_carte_no(curent_seletedno);
			if ("0".equals(operateLogo)) {
				//添加
				result=carteDao.insert(carte);
			}else{
				//修改
				result=carteDao.update(carte);
			}
		
		}
		memCache.delete("carteList"+tag_system);
		memCache.delete("carteCache"+tag_system);
		return result;
	}
	
	/**
	 * ********************************************************
	 * @Title: prepare
	 * @Description: 查看菜单
	 * @return Map
	 * @date 2016-01-13 下午 15:38:34 
	 ********************************************************
	 */
	public Map<String, Object> prepare(String carteNo, String tag_system)
			throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			if (carteNo!=null && !"".equals(carteNo)) {
				Map<String, Map<String, Object>> carte=(Map<String, Map<String, Object>>)memCache.get("carteCache"+tag_system);
				List<Map<String, Object>> carteList=(List<Map<String, Object>>)memCache.get("carteList"+tag_system);
				map=carte.get(carteNo);
				//获取父节点
				String parentCarteNo=(String)map.get("PARENT_CARTE_NO");
				//默认为根节点名称
				String parentCarte="主菜单";
				//该菜单不是根节点，则获取该几点的父节点名称
				if (!"0".equals(parentCarteNo)) {
					for (Map<String, Object> mapC : carteList) {
						if (parentCarteNo.equals((String)mapC.get("CARTE_NO"))) {
							parentCarte=(String)mapC.get("CARTE_NAME");
							break;
						}
					}
				}
				map.put("PARENTCARTE", parentCarte);
				map.put("PARENTROOT", parentCarteNo);
				map.put("OPERATELOGO", "1");
				map.put("CARTE_ID", map.get("ID"));
				System.out.println("-------------------------:"+map.get("ID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			
		}
		return map;
	}
	
	/****************辅助方法*****************/
	/**
	 * 获取当前菜单的编号
	 * @param currentNo
	 * @param levels
	 * @return
	 */
	public String getCarteNo(String currentNo,int levels){
		String carteNoTmp=(Integer.parseInt(currentNo.substring(levels*2-2,levels*2))+1)+"";
		return currentNo.substring(0,levels*2-2)+(carteNoTmp.length()==2?carteNoTmp:"0"+carteNoTmp)+currentNo.substring(levels*2);
	}
	/**
	 * 获取选择的父菜单的级别
	 * @param carteNo
	 * @return
	 */
	public int getLevels(String parentCarteNo){
		int level=0;
		String carteNoTmp="";
		for (int i = 0; i < parentCarteNo.length(); i=i+2) {
			if (i!=parentCarteNo.length()-2) {
				carteNoTmp=parentCarteNo.substring(i,i+2);
			}else{
				carteNoTmp=parentCarteNo.substring(i);
			}
			if (!"00".equals(carteNoTmp)) {
				level++;
			}
		}
		return level;
	}

	/**
	 * ********************************************************
	 * @Title: clearCache
	 * @Description: 清除缓存
	 * @return int
	 * @date 2016-01-13 下午 15:38:34 
	 ********************************************************
	 */
	public int clearCache(String tag_system) throws Exception {
		try {
			memCache.delete("carteList"+tag_system);
			memCache.delete("carteCache"+tag_system);
			return 0;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * ********************************************************
	 * @Title: delete
	 * @Description: 删除菜单
	 * @return int
	 * @date 2016-01-14 下午 01:55:34 
	 ********************************************************
	 */
	@Transactional
	public Map<String,String> delete(Integer id, String tag_system) throws Exception {
		Map<String,String> resultMap=new HashMap<String, String>();
		try {
			Map map=new HashMap();
			map.put("id", id);
			map.put("system_tag", tag_system);
			//查询
			Carte carte=carteDao.getOne("getOneByIdAndSystem", map);
			if (carte!=null && carte.getId()!=null) {
				//查询该菜单下是否还有子菜单
				map.put("parent_carte_no", carte.getCarte_no());
				int count=carteDao.getNumber("getCountByNo", map);
				if (count>0) {
					resultMap.put("resultCode", "1");
					resultMap.put("resultReason", "该菜单下有子菜单，不能直接删除");
				}else{
					//删除菜单
					int result=carteDao.delete("delete", map);
					//删除该菜单下的按钮
					map.put("carte_id", id);
					result=permissionDao.delete("deleteByCarte", map);
					resultMap.put("resultCode", "0");
					resultMap.put("carteName", carte.getCarte_name());
					resultMap.put("resultReason", "");
				}
			}else{
				resultMap.put("resultCode", "1");
				resultMap.put("resultReason", "未查找到数据");
			}
			memCache.delete("carteList"+tag_system);
			memCache.delete("carteCache"+tag_system);
		} catch (Exception e) {
			throw e;
		}
		return resultMap;
	}

	/**
	 * ********************************************************
	 * @Title: publicMenus
	 * @Description: 赋菜单权限时获取菜单
	 * @return Map
	 * @date 2016-01-22下午 01:55:34 
	 ********************************************************
	 */
	public Map<String, Object> publicMenus(String user_code,UserInfo loginUser,String user_logo) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		int system_tag=0;
		//查询当前用户信息
		if ("0".equals(user_logo)) {
			UserInfo userInfo=userInfoService.getOne(user_code);
			system_tag=userInfo.getSystem_tag();
		}
		
		//获取当前登录用户所拥有的菜单(从数据库获取)
		List<Map<String, Object>> loginUserCarteList=new ArrayList<Map<String,Object>>();
		if (loginUser.getRole_no()==0) {//管理员
			loginUserCarteList=carteDao.getList("getAllCarteByTag",system_tag);
		}else{
			Map<String,Object> tmpMap=new HashMap<String, Object>();
			tmpMap.put("user_code", loginUser.getUser_code());
			tmpMap.put("system_tag", system_tag);
			loginUserCarteList=carteDao.getList("getCartebyUserCode",tmpMap);
		}
		//获取当前选中用户所拥有的菜单
		Map<String,Object> tmpMap=new HashMap<String, Object>();
		tmpMap.put("user_code", user_code);
		tmpMap.put("system_tag", system_tag);
		List<Map<String, Object>> selectedUserCarteList=carteDao.getList("getCartebyUserCode", tmpMap);
		
		//组装菜单
		StringBuffer carteJson=new StringBuffer("[");
		int count=0;
		for (int i = 0; i < loginUserCarteList.size(); i++) {
			Map<String, Object> loginTmp=loginUserCarteList.get(i);
			if (selectedUserCarteList.size()!=0) {
				if (count>selectedUserCarteList.size()-1) {
					count=selectedUserCarteList.size()-1;
				}
			}
			
			String parent_no=loginTmp.get("PARENT_CARTE_NO")+"";
			if (parent_no!=null) {
				if (parent_no.length()>1 && !parent_no.equals("-1")) {
					parent_no = parent_no.substring(parent_no.length() - 8, parent_no.length());
				}
			}
			carteJson.append("{parentNo:\"" + loginTmp.get("PARENT_CARTE_NO") + "\",carteNo:\""
					+ loginTmp.get("CARTE_NO") + "\",carteId:\"" +loginTmp.get("ID")
					+ "\",name:\"" + loginTmp.get("CARTE_NAME") + "\"");
			
			if (selectedUserCarteList!=null) {
				if (selectedUserCarteList.size() != 0){
					if ((loginTmp.get("CARTE_NO").equals(selectedUserCarteList.get(count).get("CARTE_NO")))
							&& (loginTmp.get("ID").equals(selectedUserCarteList.get(count).get("ID")))) {
						carteJson.append(",checked:true");
						count++;
					}
				}
				carteJson.append("}");
			}
			
			if (loginUserCarteList.size()-1>i) {
				carteJson.append(",");
			}
			
		}
		carteJson.append("]");
		
		map.put("system_tag", system_tag);
		map.put("carteJson", carteJson.toString());
		return map;
	}
	
}

