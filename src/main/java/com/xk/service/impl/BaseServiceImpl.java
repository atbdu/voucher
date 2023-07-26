package com.xk.service.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.xk.filter.TokenComponent;
import com.xk.util.RedisUtil;
import org.springframework.stereotype.Service;

import com.xk.dao.BaseDao;
import com.xk.entity.Page;
import com.xk.service.BaseService;
import com.xk.util.MemCached;

import javax.annotation.Resource;

@Service
@SuppressWarnings("all")
public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {
	
	MemCached memCache=MemCached.getInstance();

	@Resource
	TokenComponent tokenComponent;
	@Resource
	RedisUtil redisUtil;

	private BaseDao<T, PK> baseDao;	
	public BaseDao<T, PK> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}
	/**------------------insert ↓----------------------------*/
	public int insert(T obj){
		return baseDao.insert(obj);
	}
	
	public int insert(String statement,Object parameter){
		return baseDao.insert(statement, parameter);
	}
	
	public int insert(String statement){
		return baseDao.insert(statement);
	}
	/**------------------update ↓----------------------------*/
	public int update(T obj){
		return baseDao.update(obj);
	}
	
	public int update(String statement,Object parameter){
		return baseDao.update(statement, parameter);
	}
	
	public int update(String statement){
		return baseDao.update(statement);
	}
	/**------------------delete ↓----------------------------*/
	public int delete(PK id){
		return baseDao.delete(id);
	}
	
	public int delete(String statement,Object parameter){
		return baseDao.delete(statement, parameter);
	}
	
	public int delete(String statement){
		return baseDao.delete(statement);
	}
	/**----------------------list-----------------------*/
	public void getPageList(Page p){
		baseDao.getPageList(p);
	}
	
	public void getPageList(String statement,Page p){
		baseDao.getPageList(statement, p);
	}
	
	public void getPageListSession2(String sql,Page p){
		baseDao.getPageListSession2(sql,p);
	}
	
	public List<Map<String,Object>> getList(Object parameter) {//获取分析不分页LIST
		return baseDao.getList(parameter);
	}
	
	public List<Map<String,Object>> getList(String statement,Object parameter){
		return baseDao.getList(statement,parameter);
	}
	
	public List getObjectList(String statement){//获取分析不分页LIST，返回Object list
		return baseDao.getObjectList(statement);
	}
	
	public List getObjectList(String statement,Object parameter){
		return baseDao.getObjectList(statement, parameter);
	}
	
	public List<LinkedHashMap<String,Object>> getLinkList(String statement){
		return baseDao.getLinkList(statement);
	}
	public List<LinkedHashMap<String,Object>> getLinkList(Object parameter){
		return baseDao.getLinkList( parameter);
	}
	public List<LinkedHashMap<String,Object>> getLinkList(String statement,Object parameter){
		return baseDao.getLinkList(statement, parameter);
	}
	
	/**----------------------select one-----------------------*/
	
	public T getOne(Object parameter){
		return baseDao.getOne(parameter);
	}
	public T getOne(String statement,Object parameter){
		return baseDao.getOne(statement,parameter);
	}
	
	
	public Map<String,Object> getOneMap(Object parameter){
		return baseDao.getOneMap(parameter);
	}
	public  Map<String,Object> getOneMap(String statement,Object parameter){
		return baseDao.getOneMap(statement,parameter);
	}
	
	/**---------------------select  number------------------*/
	public Integer getNumber(String statement){
		return baseDao.getNumber(statement);
	}
	
	public Integer getNumber(String statement,Object parameter){
		return baseDao.getNumber(statement,parameter);
	}

	public List<Map<String, Object>> getList(String statement) {
		return baseDao.getList(statement);
	}

	public Object getObject(String statement) {
		return baseDao.getObject(statement);
	}

	public Object getObject(String statement, Object parameter) {
		return baseDao.getObject(statement, parameter);
	}
	
	
}
