
package com.xk.util;

/**
 * 
 *********************************************************.<br>
 * [类名] OtherMethod <br>
 * [描述] TODO(字符串自动补齐工具类) <br>
 * [作者] 陈勇磊 <br>
 * [时间] 2016年1月28日 上午9:44:57 <br>
 *********************************************************.<br>
 */
public class OtherMethod {
	
	/**
	 * ********************************************************
	 * @Title: addFrontString
	 * @Description: 左边补字符
	 * @param data  要补全的数据
	 * @param num  总长度
	 * @param str  要补 的字符 
	 * @return String
	 * @date 2013-6-27 下午04:04:02
	 ********************************************************
	 */
	public static String addString_left(String data , int num ,String str){
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < num-data.length(); i++) {
			result.append(str);
		}
		result.append(data);
		return result.toString();
	}
	
	/**
	 * ********************************************************
	 * @Title: addFrontString
	 * @Description: 右边补字符
	 * @param data  要补全的数据
	 * @param num  总长度
	 * @param str  要补 的字符 
	 * @return String
	 * @date 2013-6-27 下午04:04:02
	 ********************************************************
	 */
	public static String addString_right(String data , int num ,String str){
		StringBuffer result = new StringBuffer(data);
		for (int i = 0; i < num; i++) {
			result.append(str);
		}
		return result.toString();
	}

}

	