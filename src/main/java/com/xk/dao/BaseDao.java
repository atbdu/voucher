package com.xk.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.xk.entity.Page;
@SuppressWarnings("all")
public interface BaseDao<T,PK extends Serializable>{
	public int insert(T obj);  //保存记录
	public int insert(String statement, Object parameter); 
	public int insert(String statement); 
	
	
	public int update(T obj); //修改记录
	public int update(String statement, Object parameter); 
	public int update(String statement); 
	
	public int delete(PK id);//根据KEY删除一条记录
	public int delete(String statement,Object parameter); 
	public int delete(String statement); 
	
	public void getPageList(Page p); //获取分析分页LIST
	public void getPageList(String statement,Page p);
	
	public void getPageListSession2(String sql,Page p); //获取分析分页LIST
	
	public List<Map<String,Object>> getList(String statement);
	public List<Map<String,Object>> getList(Object parameter);//获取分析不分页LIST
	public List<Map<String,Object>> getList(String statement,Object parameter);
	public List getObjectList(String statement);//获取分析不分页LIST，返回Object list
	public List getObjectList(String statement,Object parameter);
	
	public List<LinkedHashMap<String,Object>> getLinkList(String statement,Object parameter);
	public List<LinkedHashMap<String,Object>> getLinkList(String statement);
	public List<LinkedHashMap<String,Object>> getLinkList(Object parameter);
	
	public T getOne(Object parameter);//获取一条记录
	public T getOne(String statement,Object parameter);//获取一条记录
	public Map<String,Object> getOneMap(Object parameter);//获取一条记录
	public Map<String,Object> getOneMap(String statement,Object parameter);//获取一条记录
	
	public Integer getNumber(String statement,Object parameter);//返回一个数据
	public Integer getNumber(String statement);//返回一个数据
	
	public Object getObject(String statement);//返回一个数据
	public Object getObject(String statement,Object parameter);//返回一个数据
	
	
	
}
