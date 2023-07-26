package com.xk.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.xk.util.Utils;
 
/**
 * ********************************************************
 * @ClassName: Page
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author nono
 * @date 2014-8-17 下午03:31:49
 * @param <T>
 *******************************************************
 */
@SuppressWarnings("all")
@Scope("prototype")
public class Page{ 
	// 排序方式
	public enum OrderDirection{
		asc, desc
	}
	private String orderField;// 排序字段
	private OrderDirection orderDirection = OrderDirection.desc;// 排序方式	
    private int pageNo = 1;//页码，默认是第一页
    private int pageSize = 50;//每页显示的记录数，默认是15
    private int totalRecord=0;//总记录数
    private int totalPage;//总页数
    private List results;//对应的当前页记录
    private String otherCtSql = "";
    private String otherValue = "";
    private HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	private Map<String, String> params=new HashMap<String,String>();//其他的参数我们把它分装成一个Map对象    
    public Page(){        	
    	Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
			String paraName=(String)enu.nextElement();
			String notInclude=",-,pageNum,orderField,orderDirection,numPerPage,totalRecord,";//参数，排除分页等参数
			System.out.println(paraName+":"+ request.getParameter(paraName).trim());
			String paravl="";
			if(notInclude.indexOf(","+paraName+",")==-1){
				paravl=request.getParameter(paraName).trim();
				params.put(paraName,paravl);
			} 	    
		}
		if(request.getParameter("totalRecord")!=null){
    		if(request.getParameter("orderField")!=null)this.setOrderField(request.getParameter("orderField"));
    		if(request.getParameter("orderDirection")!=null)this.setOrderDirection(request.getParameter("orderDirection").equals("desc")?OrderDirection.desc:OrderDirection.asc);
    		this.setPageNo(Integer.parseInt(request.getParameter("pageNum")));
    		this.setPageSize(Integer.parseInt(request.getParameter("numPerPage")));
    		if(request.getParameter("totalRecord")!=null)this.setTotalRecord(Integer.parseInt(request.getParameter("totalRecord")));
    	}
    }

    public String getOtherCtSql() {
        return otherCtSql;
    }

    public void setOtherCtSql(String otherCtSql) {
        this.otherCtSql = otherCtSql;
    }

    public String getOtherValue() {
        return otherValue;
    }

    public void setOtherValue(String otherValue) {
        this.otherValue = otherValue;
    }

    public int getPageNo(){
       return pageNo;
    } 
    public void setPageNo(int pageNo) {
       this.pageNo = pageNo;
    }
 
    public int getPageSize() {
       return pageSize;
    }
 
    public void setPageSize(int pageSize) {
       this.pageSize = pageSize;
    }
 
    public int getTotalRecord() {
       return totalRecord;
    }
 
    public void setTotalRecord(int totalRecord) {
       this.totalRecord = totalRecord;
       //在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
       int totalPage = totalRecord%pageSize==0 ? totalRecord/pageSize : totalRecord/pageSize + 1;
       this.setTotalPage(totalPage);
    }
 
    public int getTotalPage() {
       return totalPage;
    }
 
    public void setTotalPage(int totalPage) {
       this.totalPage = totalPage;
    }
 
    public List getResults() {
       return results;
    }
 
    public void setResults(List results) {
       this.results = results;
    }
    
    public String getJsonPage(){
       String keytype="";
       List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
       Map<String,Object> page_json=new HashMap<String,Object>();
 	   for(int i=0;i<results.size();i++){
 			Map<String,Object> map=(Map<String, Object>) results.get(i);		
 		    for (String key:map.keySet()) {
 		    	if(map.get(key)!=null){
 		    	keytype=map.get(key).getClass().toString();
 		    	
// 		    	if(keytype.indexOf("Timestamp")!=-1){  //时间类型转string
// 		    		String date_str=Utils.dateToStr((java.sql.Timestamp)map.get(key), "yyyy-MM-dd HH:mm:ss");
// 		    		map.put(key, date_str);
// 		    	}
 		    	map.put(key, map.get(key).toString());
 		    	}else{
 		    		map.put(key, "");
 		    	}
 		    }
 		   list.add(map);
 	   }
 	  page_json.put("rows", list);
 	  page_json.put("total",this.getTotalRecord());
		JSONObject json = JSONObject.fromObject(page_json);//将java对象转换为json对象
		return json.toString();//将json对象转换为字符串
    }
   
    public Map<String, String> getParams() {
       return params;
    }
   
    public void setParams(Map<String, String> params) {
    	((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().setAttribute("params", params);
       this.params = params;
    }
    
	public String getOrderField() {
		
		return orderField;
	}
	public void setOrderField(String orderField) {
	
		this.orderField = orderField;
	}
	public OrderDirection getOrderDirection() {	
		return orderDirection;
	}
	public void setOrderDirection(OrderDirection orderDirection) {	
		this.orderDirection = orderDirection;
	}
    @Override
    public String toString() {
       StringBuilder builder = new StringBuilder();
       builder.append("Page [pageNo=").append(pageNo).append(", pageSize=")
              .append(pageSize).append(", results=").append(results).append(
                     ", totalPage=").append(totalPage).append(
                     ", totalRecord=").append(totalRecord).append("]");
       return builder.toString();
    }
    
 
}

	