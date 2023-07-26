
package com.xk.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.xk.entity.BaseInfo;
import com.xk.entity.BaseInfoClass;
import com.xk.controller.BaseController;
import com.xk.dao.BaseInfoDao;
import com.xk.service.BaseInfoService;
import com.xk.util.DateUtil;
import com.xk.util.MemCached;

import javax.annotation.Resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* ********************************************************
* @ClassName: BaseInfoServiceImpl
* @Description: 基本详细信息表
* @author 自动生成
* @date 2016-01-12 下午 08:45:56 
*******************************************************
*/
@SuppressWarnings("all")
@Service
public class BaseInfoServiceImpl extends BaseServiceImpl<BaseInfo,Integer> implements BaseInfoService{

	@Autowired
	private BaseInfoDao baseInfoDao;

	@Autowired
	public void setBaseDao(BaseInfoDao bDao) {
		super.setBaseDao(baseInfoDao);
	}
	@Transactional
	public void save(BaseInfo  baseInfo) throws Exception {
		baseInfo.setAdd_date(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		baseInfo.setAdd_user(BaseController.getUserCode());
		 //如果不选状态 默认是未启用
		 if (baseInfo.getStatus()==null) {
			 baseInfo.setStatus(0);
		 }
		 if (baseInfo.getId()==null) {
			 baseInfoDao.insert(baseInfo);
		 }else {
			 baseInfoDao.update(baseInfo);
		 }
		 MemCached.getInstance().delete(baseInfo.getClass_en());
	}
	//删除
	@Transactional
	public Map<String,String> remove(Integer id) throws Exception {
			Map<String,String> map=new HashMap<String, String>();
			BaseInfo bi=baseInfoDao.getOne(id);
			if (bi==null) {
				map.put("resultCode", "1");
				map.put("remark", "编号为："+id+"的基本详细信息不存在");
			}else{
				baseInfoDao.delete(id);
				map.put("resultCode", "0");
				map.put("remark", "编号为："+id+"的基本详细信息[标识："+bi.getInfo_en()+",值："+bi.getInfo()+",所属："+bi.getClass_en()+"]删除成功");
			}
			 MemCached.getInstance().delete(bi.getClass_en());			
			return map;
	}
}

