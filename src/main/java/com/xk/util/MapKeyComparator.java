package com.xk.util;

import java.util.Comparator;

public class MapKeyComparator implements Comparator<String>{
	/**
	 ********************************************************* 
	 * @Title: compare
	 * @Description: 比较器
	 * @return int
	 * @date 2016-3-23
	 ******************************************************** 
	 */
	public int compare(String str1, String str2) {
		return str1.compareTo(str2);
	}

}
