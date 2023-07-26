package com.xk.aop;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.xk.entity.UserInfo;
import com.xk.service.UserActionService;


/**
 *  行为记录详细的操作记录
 * @author zyy
 *
 */
@Aspect
@Component
public class ManagerAop{
	@Resource
	private HttpServletRequest request;
	@Resource
	private UserActionService userActionService;
	//定义切入点
	@Pointcut("execution (* com.xk.controller.*.*(..))"
				+ " && !execution (* com.xk.controller.*.set*(..))"
				+ " && !execution (* com.xk.controller.*.get*(..))"
				+ " && !execution (* com.xk.controller.*.list*(..))"
				+ " && !execution (* com.xk.controller.*.public*(..))"
				+ " && !execution (* com.xk.controller.*.find*(..))")
	public void afterPointcut(){
	}
	//方法后执行
	@After("afterPointcut()")
	public void after(JoinPoint jp){
		@SuppressWarnings("unchecked")
		Map<String,Object> data=(Map<String, Object>) request.getAttribute("useraction");
		if(null!=data ){
			UserInfo user=(UserInfo) request.getSession().getAttribute("user");
			data.put("user_code", data.get("user_code")==null?user.getUser_code():data.get("user_code"));
			data.put("system_tag", 0);
			userActionService.insert("insertUseraction", data);
		}
	}
	//方法前执行
	@Before("afterPointcut()")
	public void before(JoinPoint jp){
	}
}
