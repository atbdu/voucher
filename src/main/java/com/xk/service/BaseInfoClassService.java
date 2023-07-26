
package com.xk.service;

import com.xk.entity.BaseInfoClass;

/**
* ********************************************************
* @ClassName: BaseInfoClassService
* @Description: 基本信息表
* @author 自动生成
* @date 2016-01-12 下午 08:46:09 
*******************************************************
*/
public interface BaseInfoClassService extends BaseService<BaseInfoClass,Integer>{
	//添加或修改
	public void save(BaseInfoClass  baseInfoClass) throws Exception;
	//删除
	public void remove(String class_en) throws Exception;
}

