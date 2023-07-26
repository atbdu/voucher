package com.xk.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class IpInstence {
	public static String s = null;
	public static String t=null;
	public static String cs = null;
	public static String ct=null;
	public static String c_t=null;
	public static String c_s=null;
	public static String x_t=null;
	public static String x_s=null;
	static {
		Properties prop = new Properties();
		InputStream in = IpInstence.class.getResourceAsStream("/rate.properties");
		try {
			prop.load(in);
			s = prop.getProperty("s").trim();
			t=prop.getProperty("t").trim();
			cs = prop.getProperty("cs").trim();
			ct=prop.getProperty("ct").trim();
			c_s = prop.getProperty("c_s").trim();
			c_t=prop.getProperty("c_t").trim();
			x_s = prop.getProperty("x_s").trim();
			x_t=prop.getProperty("x_t").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
public static void main(String[] args) {
	System.out.println(new IpInstence().s.toString()+"****"+new IpInstence().t.toString()+"****"
			+"****"+new IpInstence().ct.toString()+"****"+new IpInstence().cs.toString()
			+"****"+new IpInstence().c_t.toString()
			+"****"+new IpInstence().c_s.toString()
			+"****"+new IpInstence().x_t.toString()
			+"****"+new IpInstence().x_s.toString());
}
}
