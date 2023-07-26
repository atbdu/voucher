
package com.xk.service.impl;

import java.awt.List;
import java.util.Date;

import com.xk.entity.BaseInfoClass;
import com.xk.controller.BaseController;
import com.xk.dao.BaseDao;
import com.xk.dao.BaseInfoClassDao;
import com.xk.service.BaseInfoClassService;
import com.xk.util.DateUtil;
import com.xk.util.MemCached;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* ********************************************************
* @ClassName: BaseInfoClassServiceImpl
* @Description: 基本信息表
* @author 自动生成
* @date 2016-01-12 下午 08:46:09 
*******************************************************
*/
@SuppressWarnings("all")
@Service
public class BaseInfoClassServiceImpl extends BaseServiceImpl<BaseInfoClass,Integer> implements BaseInfoClassService{

	@Autowired
	private BaseInfoClassDao baseInfoClassDao;

	@Autowired
	public void setBaseDao(BaseInfoClassDao bDao) {
		super.setBaseDao(baseInfoClassDao);
	}
	//添加修改
	@Transactional
	public void save(BaseInfoClass  baseInfoClass) throws Exception {
		baseInfoClass.setAdd_date(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		 baseInfoClass.setAdd_user(BaseController.getUserCode());
		 //如果不选状态 默认是未启用
		 if (baseInfoClass.getStatus()==null) {
			baseInfoClass.setStatus(0);
		 }
		 if (baseInfoClassDao.getOne(baseInfoClass.getClass_en())==null) {
			 baseInfoClassDao.insert(baseInfoClass);
		 }else {
			 baseInfoClassDao.update(baseInfoClass);
		 }
		 MemCached.getInstance().delete("base_info_class");
	}
	//删除
	@Transactional
	public void remove(String class_en) throws Exception {
			baseInfoClassDao.delete("deletes",class_en);
			MemCached.getInstance().delete("base_info_class");
		
	}
}

