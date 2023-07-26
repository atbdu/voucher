package com.xk.util;


import java.io.BufferedReader;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpPost {
	
	/**
	 * 公用的模拟网页发送请求的方法
	 * @param url
	 * @param map
	 */
	public static String sendPost(String url, Map<String, String> map){
		
		/**
		 * 首先要和URL下的URLConnection对话,URLConnection可以很容易的从URL得到
		 * 使用页面发送请求的正常流程:页面的URL
		 * 使用java程序发送求情的流程,使用UrlConnection向页面发送请求,并传递两个参数:用户名和密码
		 * 然后使用程序获取验证结果
		 */
		try {
			URL requestUrl = new URL(url);
			URLConnection connection = requestUrl.openConnection();
			/**
			 * 然后把连接设为输出模式,URLConnection通常作为输出来使用
			 * 通过把URLConnection设为输出,可以把数据向指定的地址传送
			 */
			connection.setDoOutput(true);
			 /**
			  * 用OutputStream,把参数约束在Writer并且放入POST信息中
			  */
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(),"8859_1");
			
			String param = "";
			for (Map.Entry<String, String> entry:map.entrySet()) {
				param=param+entry.getKey()+"="+entry.getValue()+"&";
			}
			out.write(param);
			out.flush();
			out.close();
			//得到服务器的响应
			String sCurrentLine = "";
			String sTotalString = "";
			InputStream is = connection.getInputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
			while((sCurrentLine = br.readLine()) != null){
				sTotalString += sCurrentLine;
			}
			return sTotalString;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String url = "http://wx.dianbingpay.com/wxpay/WeiXin/getTicket";
		Map<String, String> map = new HashMap<String, String>();
		map.put("client_no", "111");
		sendPost(url, map);
	}
	
}
