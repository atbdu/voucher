
package com.xk.controller;


import com.xk.entity.BaseInfoClass;
import com.xk.service.BaseInfoClassService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* ********************************************************
* @ClassName: BaseInfoClassController
* @Description: 基本信息表
* @author 自动生成
* @date 2016-01-12 下午 08:46:09 
*******************************************************
*/
@SuppressWarnings("all")
@Scope("prototype")
@Controller
@RequestMapping("/BaseInfoClass")
public class BaseInfoClassController extends BaseController{

	@Resource
	private BaseInfoClassService baseInfoClassService;

	/**
	 * ********************************************************
	 * @Title: list
	 * @Description: 分页,条件查询
	 * @return String
	 * @date 2016-01-12 下午 08:46:09 
	 ********************************************************
	 */
	 @RequestMapping("/list")
	public String list(){
		//获取类型
		List<Map<String, Object>> keymap=baseInfoClassService.getList("getKeyByValue");
		this.setAttribute("keyvaluelist", keymap);
		//baseInfoClassService.getPageList(this.getPage());
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
			baseInfoClassService.getPageList(this.getPage());
			repalceCache(this.getPage(),"b_status","status");
			return this.getPage().getJsonPage();
	    }


	/**
	 * ********************************************************
	 * @Title: add
	 * @Description: 添加、显示
	 * @return String
	 * @date 2016-01-12 下午 08:46:09 
	 ********************************************************
	 */
	 @RequestMapping("/add")
	public String add(@RequestParam(value="class_en",required=false) String class_en){
		 BaseInfoClass baseInfoClass=new BaseInfoClass();
		if(class_en!=null){
			baseInfoClass = baseInfoClassService.getOne(class_en) ;
		}else{
			baseInfoClass.setStatus(0);
		}
		this.setAttribute("baseInfoClass",baseInfoClass);
		return this.display();
	}

	/**
	 * ********************************************************
	 * @Title: save
	 * @Description: 修改保存
	 * @return String
	 * @date 2016-01-12 下午 08:46:09 
	 ********************************************************
	 */
	 @RequestMapping("/save")
	public @ResponseBody Map save(@ModelAttribute("BaseInfoClass") BaseInfoClass  baseInfoClass){
		 try {
			baseInfoClassService.save(baseInfoClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success("保存["+baseInfoClass.getClass_cn()+"]成功","");
	}

	/**
	 * ********************************************************
	 * @Title: delete
	 * @Description: 删除
	 * @return String
	 * @date 2016-01-12 下午 08:46:09 
	 ********************************************************
	 */
	 @RequestMapping("/delete")
	public @ResponseBody Map delete(@RequestParam String class_en){
		try {
			baseInfoClassService.remove(class_en);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return success("删除成功","");
	}
	 /**
		 * ********************************************************
		 * 
		 * @Title: checkclassen
		 * @Description: TODO(检查控件是否已存在)
		 * @param name
		 * @return Integer
		 * @date 2015-6-26 下午4:50:24
		 ******************************************************** 
		 */
		@SuppressWarnings("finally")
		@RequestMapping("/checkclassen")
		public @ResponseBody String checkclassen(@RequestParam String class_en) {
			Integer date = 0;
			String result = "true";
			try {
				date = baseInfoClassService.getNumber("checkclassen", class_en);
				if (date>0) {
					result="false";
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				return result;
			}
		}

}

