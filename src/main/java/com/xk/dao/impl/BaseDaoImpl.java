package com.xk.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.xk.conn.BaseConn;
import com.xk.dao.BaseDao;
import com.xk.entity.Page;

import javax.annotation.Resource;

@Repository
@SuppressWarnings("all")
public class BaseDaoImpl<T,PK extends Serializable> extends SqlSessionDaoSupport implements BaseDao<T,PK> {
	public  static final String SQLNAME_SEPARATOR =  ".";
	public  static final String SQL_INSERT        =  "insert";
	public  static final String SQL_UPDATE        =  "update";
	public  static final String SQL_DELETE        =  "delete";
	public  static final String SQL_GETBYID       =  "getById";
	public  static final String SQL_GETPAGELIST   =  "getPageList";	
	public  static final String SQL_GETLIST       =  "getList";	
	public  static final String SQL_GETONE        =  "getOne";
	public  static final int    expireTime 		  =  60;
	private              String sqlNamespace      =  this.getNamespace();
	/**--------------------------------------------start---set-get---------------------**/
	public String getSqlNamespace() {
		return sqlNamespace;
	}

	public void setSqlNamespace(String sqlNamespace) {
		this.sqlNamespace = sqlNamespace;
	}
	/**--------------------------------------------end---set-get---------------------**/
	
	@Resource(name = "sqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@SuppressWarnings("unchecked")
	public String getNamespace() {	
		Class<T> clazz =null;
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            clazz = (Class<T>) parameterizedType[0];           
            return clazz.getSimpleName();          
        }
        return null;
	}
	
	/**
	 * 获取SQL语句
	 * @param sqlName
	 * @return
	 */
	protected String getSqlName(String sqlName) {
		return sqlNamespace + SQLNAME_SEPARATOR + sqlName;
	}
	/**----------------------------------insert ↓-----------------------------------------**/
	/**
	 * 添加数据
	 * @param obj
	 * @return int 影响条数或主键id
	 */
	public int insert(T obj){
		return this.getSqlSession().insert(getSqlName(SQL_INSERT), obj);
	}
	/**
	 * 指定SQL语句传入参数插入
	 * @param statement  sql语句id
	 * @param parameter  参数
	 * @return  影响条数或主键id
	 */
	public int insert(String statement, Object parameter) {
		return this.getSqlSession().insert(getSqlName(statement), parameter);
	}
	/**
	 *指定SQL语句无参数插入
	 * @param statement
	 * @return
	 */
	public int insert(String statement) {	
		return this.getSqlSession().insert(getSqlName(statement));
	}	
	/**----------------------------------update ↓-----------------------------------------**/
	/**
	 * 修改数据
	 * @param obj
	 * @return 影响条数
	 */
	public int update(T obj){
		return this.getSqlSession().update(getSqlName(SQL_UPDATE), obj);
	}
	/**
	 * 指定SQL语句id,传入参数修改
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public int update(String statement, Object parameter) {
		return this.getSqlSession().update(getSqlName(statement), parameter);
	} 
	/**
	 * 指定SQL语句id固定修改
	 * @param statement
	 * @return
	 */
	public int update(String statement) {
		return this.getSqlSession().update(getSqlName(statement));
	} 
	/**----------------------------------delete ↓-----------------------------------------**/
	/**
	 * 根据主键删除一条记录
	 * @param id
	 * @return 影响条数
	 */
	public int delete(PK id){
		return this.getSqlSession().delete(getSqlName(SQL_DELETE), id);
	}
	/**
	 * 根据指定SQL语句，传入参数删除
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public int delete(String statement,Object parameter){
		return this.getSqlSession().delete(getSqlName(statement), parameter);
	}
	/**
	 * 根据指定的SQL语句删除
	 * @param statement
	 * @return
	 */
	public int delete(String statement){
		return this.getSqlSession().delete(statement);
	}
	/**----------------------------------select ↓-----------------------------------------**/
	
	/**------select list-----**/	
	/**获取翻页的数据LIST
	 * @param Page p
	 * @return List 结果LIST map 模式
	 */
	public void getPageList(Page p){
		p.setResults(this.getSqlSession().selectList(getSqlName(SQL_GETPAGELIST), p));		
	}
	
	public void getPageList(String statement, Page p) {
		p.setResults(this.getSqlSession().selectList(getSqlName(statement), p));		
	}
	
	public void getPageListSession2(String sql,Page p){
		p.setResults(BaseConn.getResultList(sql,p));		
	}
	
	public List<Map<String,Object>> getList(Object parameter){
		return this.getSqlSession().selectList(getSqlName(this.SQL_GETLIST), parameter);
	}
	
	public List<Map<String, Object>> getList(String statement,Object parameter){
		return this.getSqlSession().selectList(getSqlName(statement), parameter);
	}
	
	public List getObjectList(String statement){//获取分析不分页LIST，返回Object list
			return this.getSqlSession().selectList(getSqlName(statement));
	}
	public List getObjectList(String statement,Object parameter){
		   return this.getSqlSession().selectList(getSqlName(statement),parameter);
	}
    
	
	public List<LinkedHashMap<String,Object>> getLinkList(String statement,Object parameter){
		return this.getSqlSession().selectList(getSqlName(statement),parameter);
	}
	public List<LinkedHashMap<String,Object>> getLinkList(String statement){
		return this.getSqlSession().selectList(getSqlName(statement));
	}
	public List<LinkedHashMap<String,Object>> getLinkList(Object parameter){
		return this.getSqlSession().selectList(getSqlName(this.SQL_GETLIST),parameter);
	}
	
	/**
	 * 获取一条记录
	 */
	public T getOne(Object parameter){
		return (T) this.getSqlSession().selectOne(getSqlName(SQL_GETONE),parameter);
	}

	public T getOne(String statement, Object parameter) {
		return (T) this.getSqlSession().selectOne(getSqlName(statement),parameter);
	}
	
	
	public Map<String,Object> getOneMap(Object parameter){
		List<Map<String,Object>> listMap=this.getSqlSession().selectList(getSqlName(SQL_GETONE),parameter);
        return listMap.size()==0?null:listMap.get(0);
		
	}
	
	public Map<String,Object> getOneMap(String statement, Object parameter) {		
		List<Map<String,Object>> listMap=this.getSqlSession().selectList(getSqlName(statement),parameter);
        return listMap.size()==0?null:listMap.get(0);
	}
	
	/**
	 * 返回一个数字
	 */
	public Integer getNumber(String statement,Object parameter){

		return (Integer) this.getSqlSession().selectOne(getSqlName(statement),parameter);
	}
	
	public Integer getNumber(String statement){
		return (Integer) this.getSqlSession().selectOne(getSqlName(statement));
	}

	public List<Map<String,Object>> getList(String statement) {
		List<Map<String,Object>> list=null;
		try {
			list=this.getSqlSession().selectList(getSqlName(statement));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 返回一个数据
	 */
	public Object getObject(String statement) {
		return this.getSqlSession().selectOne(getSqlName(statement));
	}

	public Object getObject(String statement, Object parameter) {
		return this.getSqlSession().selectOne(getSqlName(statement),parameter);
	}
	
	
}
