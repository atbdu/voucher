
package com.xk.realtimeutil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Util {

	/**
	 * 
	 *********************************************************.<br>
	 * [方法] getDate <br>
	 * [描述] 获取时间 <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] String <br>
	 * [时间] 2017-1-3 下午06:32:43 <br>
	 *********************************************************.<br>
	 */
	public static String getDate(Date date,String dateFormat){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String dateStr = sdf.format(date);
		return dateStr;
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] getChineseByStatus <br>
	 * [描述] 通过交易状态获取中文解释 <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] String <br>
	 * [时间] 2017-1-4 上午10:40:38 <br>
	 *********************************************************.<br>
	 */
	public static String getChineseByStatus(Integer status){
		Map<Integer, String> m = new HashMap<Integer, String>();
		m.put(0, "成功");
		m.put(-1, "撤销");
		m.put(-2, "待撤销");
		m.put(-3, "冲正");
		m.put(-4, "待冲正");
		m.put(1, "补录");
		m.put(2, "失败");
		m.put(3, "异常");
		m.put(6, "冻结");
		return m.get(status);
	}
	
}

	