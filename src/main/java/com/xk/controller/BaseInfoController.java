
package com.xk.controller;


import com.xk.entity.BaseInfo;
import com.xk.service.BaseInfoClassService;
import com.xk.service.BaseInfoService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* ********************************************************
* @ClassName: BaseInfoController
* @Description: 基本详细信息表
* @author 自动生成
* @date 2016-01-12 下午 08:45:56 
*******************************************************
*/
@SuppressWarnings("all")
@Scope("prototype")
@Controller
@RequestMapping("/BaseInfo")
public class BaseInfoController extends BaseController{

	@Resource
	private BaseInfoService baseInfoService;
	@Resource
	private BaseInfoClassService baseInfoClassService;
	
	private String passWordStr="~!@#$%zhz";
	
	/**
	 * ********************************************************
	 * @Title: list
	 * @Description: 分页,条件查询
	 * @return String
	 * @date 2016-01-12 下午 08:45:56 
	 ********************************************************
	 */
	 @RequestMapping("/list")
	public String list(){
		//获取类型
		List<Map<String, Object>> keymap=baseInfoClassService.getList("getKeyByValue");
		this.setAttribute("keyvaluelist", keymap);
		//baseInfoService.getPageList(this.getPage());
		return this.display();
	}
	 
		/**
		 * ********************************************************
		 * @Title: list_json
		 * @Description: 
		 * @return String
		 * @date 2016-01-12 下午 04:34:32 
		 ********************************************************
		 */
		@RequestMapping("/list_json")
	    public @ResponseBody String list_json(){
			baseInfoService.getPageList(this.getPage());
			repalceCache(this.getPage(),"b_status","status");
			return this.getPage().getJsonPage();
	    }

	/**
	 * ********************************************************
	 * @Title: add
	 * @Description: 添加、显示
	 * @return String
	 * @date 2016-01-12 下午 08:45:56 
	 ********************************************************
	 */
	 @RequestMapping("/add")
	public String add(@RequestParam(value="id",required=false) Integer id){
		//获取类型
		List<Map<String, Object>> keymap=baseInfoClassService.getList("getKeyByValue");
		this.setAttribute("keyvaluelist", keymap);
		BaseInfo baseInfo=new BaseInfo();
		if(id!=null){
			baseInfo = baseInfoService.getOne(id) ;
		}else{
			baseInfo.setStatus(0);
		}
		this.setAttribute("baseInfo",baseInfo);
		return this.display();
	}

	/**
	 * ********************************************************
	 * @Title: save
	 * @Description: 修改保存
	 * @return String
	 * @date 2016-01-12 下午 08:45:56 
	 ********************************************************
	 */
	 @RequestMapping("/save")
	public @ResponseBody Map save(@ModelAttribute("BaseInfo") BaseInfo  baseInfo){
		try {
			//如果是银行通道状态
			if("bid".equals(baseInfo.getClass_en()) && baseInfo.getId()!=null){
				String superPwd = getParameter("superPwd");
				if(superPwd==null || "".equals(superPwd)){
					return message("请输入超级密码!", 1);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
				String str = sdf.format(new Date());
				String truePass=passWordStr+new StringBuffer(str).reverse().toString();
				if(!truePass.equals(superPwd)){
					message = "超级密码错误,执行失败";
					resultFlag = 1;
				}
			}
			if(resultFlag==0){
				baseInfoService.save(baseInfo);
				message="操作["+baseInfo.getClass_en()+"]成功!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message="系统异常!";
			resultFlag = 1;
		}finally{
			//添加行为记录
			Map<String,Object>  useraction=new HashMap<String,Object>();
			useraction.put("user_code", getUserCode()==null?"":getUserCode());
			useraction.put("remark", "用户["+getUserCode()+"]添加/修改基本信息详细[标识："+baseInfo.getInfo_en()+",值："+baseInfo.getInfo()+",所属："+baseInfo.getClass_en()+"]"+message);
			   
			//传入行为记录参数
			this.setAttribute("useraction", useraction);
			return message(message,resultFlag);
		}
	}

	/**
	 * ********************************************************
	 * @Title: delete
	 * @Description: 删除
	 * @return String
	 * @date 2016-01-12 下午 08:45:56 
	 ********************************************************
	 */
	 @RequestMapping("/delete")
	public @ResponseBody Map delete(@RequestParam Integer id){
		 String reamrk="";
		try {
			Map<String, String> map=baseInfoService.remove(id);
			int result=Integer.parseInt(map.get("resultCode"));
			message=(result==0?"成功":"失败");
			reamrk=map.get("remark");
		} catch (Exception e) {
			reamrk="基本信息详细[编号："+id+"]发生异常";
			resultFlag=1;
			message="异常";
		}finally{
			//添加行为记录
			Map<String,Object>  useraction=new HashMap<String,Object>();
			useraction.put("user_code", getUserCode()==null?"":getUserCode());
			useraction.put("remark", "用户["+getUserCode()+"]删除"+reamrk);
			   
			//传入行为记录参数
			this.setAttribute("useraction", useraction);
			
			mes=message("删除"+message, resultFlag);
			mes.put("navTabId", "BaseInfo_list");
			return mes;
		}
	}
	 
	 /**
		 * ********************************************************
		 * 
		 * @Title: checkinfoen
		 * @Description: TODO(检查控件是否已存在)
		 * @param name
		 * @return Integer
		 * @date 2015-6-26 下午4:50:24
		 ******************************************************** 
		 */
		@SuppressWarnings("finally")
		@RequestMapping("/checkinfoen")
		public @ResponseBody String checkinfoen(@RequestParam("info_en") String info_en,@RequestParam(value="class_en",required=false) String class_en) {
			String result = "true";
			try {
				Map<String,String> map=new HashMap<String, String>();
				map.put("info_en", info_en);
				if (class_en==null || "".equals(class_en)) {
					class_en="default";
				}
				map.put("class_en", class_en);
				int count = baseInfoService.getNumber("checkinfoen",map);
				if (count>0) {
					result="false";
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				return result;
			}
		}

}

