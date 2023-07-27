
package com.xk.controller;


import com.xk.entity.Carte;
import com.xk.service.CarteService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* ********************************************************
* @ClassName: CarteController
* @Description: 菜单
* @author 自动生成
* @date 2016-01-12 下午 09:38:34 
*******************************************************
*/
@SuppressWarnings("all")
@Scope("prototype")
@Controller
@RequestMapping("/Carte")
public class CarteController extends BaseController{

	@Resource
	private CarteService carteService;

	/**
	 * ********************************************************
	 * @Title: list
	 * @Description: 分页,条件查询
	 * @return String
	 * @date 2016-01-12 下午 09:38:34 
	 ********************************************************
	 */
	 @RequestMapping("/list")
	public String list(){
		 //显示的面板 0-菜单、1-按钮、2-数据权限
		String show_tab=getParameter("show_tab")==null?"0":getParameter("show_tab");
		//平台标识 0-总后台、1-财务、2-代理商、3-商户
		String tag_system=getParameter("tag_system")==null?"0":getParameter("tag_system");
		//获取菜单
		List<Object> carteList=null;
		try {
			carteList = carteService.carteList(tag_system);
			message="成功!";
		} catch (Exception e) {
			e.printStackTrace();
			message="异常!";
		}finally{
			setAttribute("show_tab", show_tab);
			setAttribute("tag_system", tag_system);
			setAttribute("cartes", carteList);
			setAttribute("map", new HashMap());
			//添加行为记录
			Map<String,Object>  useraction=new HashMap<String,Object>();
			useraction.put("user_code", getUserCode());
			useraction.put("remark", "用户["+getUserCode()+"]查看菜单管理"+message);
			   
			//传入行为记录参数
			this.setAttribute("useraction", useraction);
			return this.display();
		}
	}
	/**
	* ********************************************************
	* @Title: publicGetMenu
	* @Description: 查询该平台下的所有菜单
	* @return String
	* @date 2016-01-12 下午 09:38:34 
	********************************************************
	*/
	@RequestMapping("/publicGetMenu")
	 public @ResponseBody List<Map<String, Object>> publicGetMenu(@RequestParam String tag_system){
		System.out.println(tag_system);
		List<Map<String, Object>> treeList = null;
		try {
			treeList=carteService.publicGetMenu(tag_system);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return treeList;
		}
	 }

	/**
	 * ********************************************************
	 * @Title: add
	 * @Description: 添加、显示
	 * @return String
	 * @date 2016-01-12 下午 09:38:34 
	 ********************************************************
	 */
	 @RequestMapping("/add")
	public String add(@RequestParam(value="id",required=false) Integer id){
		if(id!=null){
			Carte carte = carteService.getOne(id) ;
			this.setAttribute("carte",carte);
		}
		return this.display();
	}

	/**
	 * ********************************************************
	 * @Title: save
	 * @Description: 修改保存
	 * @return String
	 * @date 2016-01-12 下午 09:38:34 
	 ********************************************************
	 */
	 @RequestMapping("/save")
	public @ResponseBody Map save(@ModelAttribute("Carte") Carte  carte,@RequestParam Map map){
		try {
			int result=carteService.saveOrUpdate(carte, map,getUserInfo());
			message=(result>0?"成功!":"失败!");
			resultFlag=(result>0?0:1);
		} catch (Exception e) {
			e.printStackTrace();
			message="异常";
			resultFlag=1;
		}finally{
			//添加行为记录
			Map<String,Object>  useraction=new HashMap<String,Object>();
			useraction.put("user_code", getUserCode()==null?"":getUserCode());
			useraction.put("remark", "用户["+getUserCode()+"]添加/修改菜单["+carte.getCarte_name()+"]"+message);
			   
			//传入行为记录参数
			this.setAttribute("useraction", useraction);
			
			mes=message("操作"+message, resultFlag);
			mes.put("navTabId", "Carte_list");
			return mes;
		}
	}

	/**
	* ********************************************************
	* @Title: prepare
	* @Description: 查看菜单
	* @return String
	* @date 2016-01-13 下午 16:38:34 
	********************************************************
	*/
	@RequestMapping("/prepare")
	 public String prepare(@RequestParam("carte_no") String carte_no,@RequestParam("tag_system") String tag_system){
//		 System.out.println(carte_no);
//		 System.out.println(tag_system);
		 try {
			 if(!carte_no.equals("0")){
				 Map<String, Object> map=carteService.prepare(carte_no, tag_system);
				 setAttribute("map", map);
			 }
			 setAttribute("tag_system", tag_system);
			 List<Object> menuList = carteService.carteList(tag_system);
			 setAttribute("menuList", menuList);
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return this.display();
		}
	 }
	 
	/**
	 * ********************************************************
	 * @Title: delete
	 * @Description: 删除
	 * @return String
	 * @date 2016-01-12 下午 09:38:34 
	 ********************************************************
	 */
	 @RequestMapping("/delete")
	public @ResponseBody Map delete(@RequestParam Integer id,@RequestParam String tag_system){
		 int result=0;
		 Map<String,String> map=new HashMap<String, String>();
		 try {
			 map=carteService.delete(id, tag_system);
			 result=Integer.parseInt(map.get("resultCode"));
			 message=(result==0?"成功!":"失败!");
			 message=message+map.get("resultReason");
			 resultFlag=(result==0?0:1);
		} catch (Exception e) {
			e.printStackTrace();
			message="异常";
			resultFlag=1;
		}finally{
			//添加行为记录
			Map<String,Object>  useraction=new HashMap<String,Object>();
			useraction.put("user_code", getUserCode()==null?"":getUserCode());
			useraction.put("remark", "用户["+getUserCode()+"]删除菜单["+map.get("carteName")+"("+id+")]"+message);
			   
			//传入行为记录参数
			this.setAttribute("useraction", useraction);
			
			mes=message("删除"+message, resultFlag);
			mes.put("navTabId", "Carte_list");
			return mes;
		}
	}
	 
	 /**
		 * ********************************************************
		 * @Title: clearCache
		 * @Description: 清除缓存
		 * @return String
		 * @date 2016-01-12 下午 09:38:34 
		 ********************************************************
		 */
		 @RequestMapping("/clearCache")
		public @ResponseBody Map clearCache(@RequestParam String tag_system){
			 int result=1;
			try {
				result=carteService.clearCache(tag_system);
			} catch (Exception e) {
				e.printStackTrace();
				result=1;
			}finally{
				if (result==0) {
					return message("清除成功!",true);
				}else{
					return message("清除失败!",false);
				}
			}
		}
		/**
		* ********************************************************
		* @Title: publicMenus
		* @Description: 获取该平台的所有菜单及当前用户的菜单（总后台）
		* @return String
		* @date 2016-01-12 下午 09:38:34 
		********************************************************
		*/
		@RequestMapping("/publicMenus")
		 public String publicMenus(@RequestParam(value="user_code") String user_code,@RequestParam(value="user_logo") String user_logo){
			try {
				Map<String, Object> map=carteService.publicMenus(user_code,getUserInfo(),user_logo);
				
				setAttribute("json", map.get("carteJson").toString());
				setAttribute("tag_system", map.get("system_tag"));
				setAttribute("user_code", user_code);
				setAttribute("user_logo", user_logo);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				return display();
			}
		 }

}

