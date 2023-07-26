package com.xk.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

public class AopExceptionHandler implements ThrowsAdvice {
	/**
	* 重写afterThrowing()方法
	*
	* @param method 执行方法
	* @param args 执行参数
	* @param target 执行实体
	* @param subclass 父类
	* @throws Throwable 异常
	*/
	public void afterThrowing(Method method, Object[] args, Object target,Throwable subclass) throws Throwable{
		 StringWriter sw = new StringWriter();
		 subclass.printStackTrace(new PrintWriter(sw, true));
         String message = sw.toString();
         //保存日志
         Utils.saveErrorLog(message);
	}

}