
package com.xk.util;

import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.xk.Encryption.EncryptionDES;
import com.xk.controller.BaseController;

import com.xk.filter.TokenComponent;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SuppressWarnings("all")
public class UrlEncryption implements TemplateMethodModel{

	private static TokenComponent tokenComponent;
	@Autowired
	private TokenComponent tokenComponent2;
	@PostConstruct
	public void init() {
		tokenComponent=tokenComponent2;
	}

	public Object exec(List arg0) throws TemplateModelException {
		String encryptionData = "";
		try {
			String user_name = BaseController.getUserCode();
			String data = arg0.get(0).toString();
//			String key = (String) MemCached.getInstance().get("encryKey_"+user_name);
			String key="";
			if (ObjectUtil.isNotNull(tokenComponent.getLoginUser(user_name))){
				key=tokenComponent.getLoginUser(user_name).getToken();
			}
			encryptionData = new EncryptionDES(key).encrypt(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptionData;
	}
	
}
 
	