
package com.xk.service;

import java.util.Map;

import com.xk.entity.BaseInfo;

/**
* ********************************************************
* @ClassName: BaseInfoService
* @Description: 基本详细信息表
* @author 自动生成
* @date 2016-01-12 下午 08:45:56 
*******************************************************
*/
@SuppressWarnings("all")
public interface BaseInfoService extends BaseService<BaseInfo,Integer>{
	//添加或修改
	public void save(BaseInfo  baseInfo) throws Exception;
	//删除
	public Map<String,String> remove(Integer id) throws Exception;
}

