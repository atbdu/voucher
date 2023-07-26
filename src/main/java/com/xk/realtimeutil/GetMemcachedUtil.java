
package com.xk.realtimeutil;

/**
 * 
 *********************************************************.<br>
 * [类名] GetMemcachedUtil <br>
 * [描述] memcached操作类 <br>
 * [作者] 博哥 <br>
 * [时间] 2017-1-3 下午05:22:03 <br>
 *********************************************************.<br>
 */
public class GetMemcachedUtil {

	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] getData <br>
	 * [描述] 获取memcached数据 <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] Map<String,String> <br>
	 * [时间] 2017-1-3 下午05:30:17 <br>
	 *********************************************************.<br>
	 */
	public static String getData(String key){
		MemCached memcached = MemCached.getInstance();
		String[] keys = Telnet.allkeys("localhost", 11220).split("\n");
		String realData = "";
        for(String s : keys){
        	if(!"".equals(s)){
	        	realData = memcached.get(s).toString(); //获取缓存数据
	        	memcached.delete(s);					//移除缓存键
	        	break;
        	}
        }
		return realData;
	}
	
}

	