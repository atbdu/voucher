package com.xk.util;

import java.util.Date;

/**
 * 随机产生一个字母+数字的字符串
 * @author Administrator
 *
 */
public class RandomUtil {

	/*
	 * 随机产生一个字母+数字的字符串
	 * @return
	 */
	public static String findTime() {
		StringBuffer randomNum = new StringBuffer();

		char a = (char) ('A' + Math.random() * ('Z' - 'A' + 1));
		String A = String.valueOf(a);
		randomNum.append(A);

		Date date = new Date();
		String id = String.valueOf(date.getTime());
		randomNum.append(id);
		
		return randomNum.toString();
	}
}
