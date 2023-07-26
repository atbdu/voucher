package com.xk.util;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ********************************************************
 * @ClassName: GetCacheMethod
 * @Description: TODO( Freemarker自定义方法 实现response.encodeURL(url)功能 )
 * @author NoNo
 * @date 2014-11-1 下午10:15:29
 *******************************************************
 */
@SuppressWarnings("all")
@Component
public class GetCacheMethod implements TemplateMethodModel {
	protected static DataCache dc;
	@Autowired
	public DataCache dc2;

	public GetCacheMethod() {

	}

	@PostConstruct
	public void init() {
		dc = dc2;
	}
    
 
      
    /** 
     * 带参数的构造函数 
     * @param response HttpServletResponse对象 
     */  
    public GetCacheMethod(HttpServletResponse response)  
    {  
    }  

    /** 
     * 执行方法 
     * @param argList 方法参数列表 
     * @return Object 方法返回值 
     * @throws TemplateModelException 
     */  
    public Object exec(List argList) throws TemplateModelException {
		int argsize=argList.size();
		switch(argsize){
			case 1:
				String method_str=(String)argList.get(0);
				if(method_str.startsWith("d_")){
					return dc.getCache((String)((String) argList.get(0)).replace("d_",""));
				}
				return dc.getDataCache((String)argList.get(0));
			case 2://菜单按钮
				return dc.getCarteButtonCache((String)argList.get(0),(String)argList.get(1));
			case 3:
				return dc.getCache((String)argList.get(0),(String)argList.get(1),(String)argList.get(2));
			case 4:
				return dc.getCache((String)argList.get(0),(String)argList.get(1),(String)argList.get(2),(String)argList.get(3));
		}
		throw new TemplateModelException("Wrong arguments!");
	}
}  