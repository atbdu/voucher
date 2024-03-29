package com.xk.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@SuppressWarnings("all")
public class CommonUtil {
	/**-----------------------cookie -- start------------*/
	/**
	 * 根据名字获取cookie
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String name){
	    Map<String,Cookie> cookieMap = ReadCookieMap(request);
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        return cookie;
	    }else{
	        return null;
	    }  
	}
	/**
	 * 基本参数
	 * @param l
	 * @return
	 */
	public static Map<String,String> listToMap(List<Map<String,Object>> l){
		Map<String,String> mm=new LinkedHashMap<String,String>();	
		for(Map<String,Object> m:l){			
			mm.put(m.get("VALUE_CONTENT").toString(),m.get("VALUE_NAME").toString());
		}
		return mm;
	}
	 
	 
	/**
	 * 将cookie封装到Map里面
	 * @param request
	 * @return
	 */
	private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){ 
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
	
	/**
	 * 设置cookie
	 * @param response
	 * @param name  cookie名字
	 * @param value cookie值
	 * @param maxAge cookie生命周期  以秒为单位
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
	    Cookie cookie = new Cookie(name,value);
	    cookie.setPath("/");
	    if(maxAge>0)  cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}	
	/**-----------------------cookie -- end------------*/
	/**
	 *某类中，是否存在某方法
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isHasMethod(String className,String methodName){
		Class cls;
		try {
			cls = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;	
		}
		Method[] mss=cls.getMethods();
		for(Method ms:mss){
			if(ms.getName().equals(methodName)){
				return true;
			}				
		}
		return false;
	}
	/**
	 * Map转List
	 */
	public static List<Object> mapToList(Map<String,Object> mp){
		List<Object> l=new ArrayList<Object>();
		if(mp==null)return null;
		for(String key:mp.keySet()){
//		for(Entry<String, Object> key:mp.entrySet()){
			l.add(mp.get(key));
		}
		return l;		
	}
	
	/**
	 * List转Map<String,Map>,某字段值做为Map的key
	 */
	public static Map<String,Map<String,Object>> listToMap(List<Map<String,Object>> l,String key){
		Map<String,Map<String,Object>> mm=new LinkedHashMap<String,Map<String,Object>>();	
		for(Map<String,Object> m:l){			
			if(m.get(key)!=null){
				mm.put(m.get(key).toString(), m);
			}
		}
		return mm;
	}
	
	/**
	 * List转Map<String,String>，从List中挑出两字段组成Map<String,String>
	 */
	
	public static Map<String,String> listToMap(List<Map<String,Object>> l,String key,String v){
		Map<String,String> mm=new LinkedHashMap<String,String>();	
		for(Map<String,Object> m:l){
			if(m.get(key)!=null){				
				mm.put(m.get(key).toString(), m.get(v).toString());
			}
		}
		return mm;
	}
	
	/**
	 * 把普通 LIST变成ZTREE要求的list
	 * @param l 
	 * @param treeKey "id","name","pId" 对应 list里哪些键值
	 * @return
	 */
	public static List<Map<String,Object>> toZtreeList(List<Map<String,Object>> l,Map<String,String> treeKey){
		List<Map<String,Object>>  nl=new ArrayList<Map<String,Object>>();		
		try{			
			for(Map<String,Object> m:l){
				Map<String,Object> current_m=new LinkedHashMap<String,Object>();
				current_m.put("id", m.get(treeKey.get("id")));
				current_m.put("name", m.get(treeKey.get("name")));
				current_m.put("pId", m.get(treeKey.get("pId")));
				nl.add(current_m);
				current_m=null;
			}		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return nl;
	}
	
	public static List<Map<String,Object>> toZtreeList(Map<String,Map<String,Object>> lm,Map<String,String> treeKey){
		List<Map<String,Object>>  nl=new ArrayList<Map<String,Object>>();
		
		try{				
			for(Entry<String, Map<String, Object>> key:lm.entrySet()){
				Map<String,Object> current_m=new LinkedHashMap<String,Object>();
				current_m.put("id", lm.get(key.toString()).get(treeKey.get("id")));
				current_m.put("name", lm.get(key.toString()).get(treeKey.get("name")));
				current_m.put("pId", lm.get(key.toString()).get(treeKey.get("pId")));
				nl.add(current_m);
				current_m=null;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return nl;
	}
	
	public static synchronized String createRandomString(int length){
		char ch[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		Random random = new Random();
	    if (length > 0) {
	      int index = 0;
	      char[] temp = new char[length];
	      int num = random.nextInt();
	      for (int i = 0; i < length % 5; i++) {
	        temp[index++] = ch[num & 15];
	        num >>= 6;
	      }
	      for (int i = 0; i < length / 5; i++) {
	        num = random.nextInt();
	        for (int j = 0; j < 5; j++) {
	          temp[index++] = ch[num & 15];
	          num >>= 6;
	        }
	      }
	      return new String(temp, 0, length);
	    }else if (length == 0) {
	      return "";
	    }else {
	      throw new IllegalArgumentException();
	    }
	}
	
}