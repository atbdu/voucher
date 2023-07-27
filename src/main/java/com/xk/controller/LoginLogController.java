
package com.xk.controller;


import com.xk.entity.LoginLog;
import com.xk.service.LoginLogService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
* ********************************************************
* @ClassName: LoginLogController
* @Description: 登录日志表
* @author 自动生成
* @date 2016-01-13 下午 06:48:10 
*******************************************************
*/
@SuppressWarnings("all")
@Scope("prototype")
@Controller
@RequestMapping("/LoginLog")
public class LoginLogController extends BaseController{

	@Resource
	private LoginLogService loginLogService;

	/**
	 * ********************************************************
	 * @Title: list
	 * @Description: 分页,条件查询
	 * @return String
	 * @date 2016-01-13 下午 06:48:10 
	 ********************************************************
	 */
	 @RequestMapping("/list")
	public String list(){
		//loginLogService.getPageList(this.getPage());
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
			loginLogService.getPageList(this.getPage());
			return this.getPage().getJsonPage();
		}

	/**
	 * ********************************************************
	 * @Title: add
	 * @Description: 添加、显示
	 * @return String
	 * @date 2016-01-13 下午 06:48:10 
	 ********************************************************
	 */
	 @RequestMapping("/add")
	public String add(@RequestParam(value="id",required=false) Integer id){
		if(id!=null){
			LoginLog loginLog = loginLogService.getOne(id) ;
			this.setAttribute("loginLog",loginLog);
		}
		return this.display();
	}

	/**
	 * ********************************************************
	 * @Title: save
	 * @Description: 修改保存
	 * @return String
	 * @date 2016-01-13 下午 06:48:10 
	 ********************************************************
	 */
	 @RequestMapping("/save")
	public @ResponseBody Map save(@ModelAttribute("LoginLog") LoginLog  loginLog){
		;
		return success("保存成功","");
	}

	/**
	 * ********************************************************
	 * @Title: delete
	 * @Description: 删除
	 * @return String
	 * @date 2016-01-13 下午 06:48:10 
	 ********************************************************
	 */
	 @RequestMapping("/delete")
	public @ResponseBody Map delete(@RequestParam Integer id){
		int result=loginLogService.delete(id);
		return success("删除成功","");
	}

}

