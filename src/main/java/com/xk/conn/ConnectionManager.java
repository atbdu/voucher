package com.xk.conn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 *********************************************************.<br>
 * [类名] ConnectionManager <br>
 * [描述] TODO(这里用一句话描述这个类的作用) <br>
 * [作者] 博哥 <br>
 * [时间] 2017-4-27 下午05:29:23 <br>
 *********************************************************.<br>
 */
public final class ConnectionManager {

	private static ConnectionManager instance;
	
	/**
	 * 
	 *********************************************************.<br>
	 * [描述] TODO(这里用一句话描述这个构造函数的作用) <br>
	 *********************************************************.<br>
	 */
	public ConnectionManager() {
	
	}
	
	 
	/**
	 * ********************************************************
	 * 
	 * @Title: getInstance
	 * @Description: C3P0 单例
	 * @return ConnectionManager
	 * @date 2013-6-27 下午02:38:56
	 ******************************************************** 
	 */
	public static final ConnectionManager getInstance() {
		if (instance == null) {
			try {
				instance = new ConnectionManager();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	/**
	 * ********************************************************
	 * @Title: getConnection
	 * @Description: 默认数据连接
	 * @return Connection
	 * @date 2013-6-27 下午02:57:42
	 ********************************************************
	 */
	public synchronized final Connection getConnection() {

		ClassPathResource cr = new ClassPathResource("dataSource.properties");//会重新加载spring框架
        Properties pros = new Properties();
        try {
            pros.load(cr.getInputStream());
        } catch (IOException ex) {
 
        }
        String DRIVER = pros.get("driver_jk").toString();
        String URL = pros.get("url_jk").toString();
        String USENAME = pros.get("username_jk").toString();
        String USEPASS = pros.get("password_jk").toString();
		Connection conn = null; 
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL,USENAME,USEPASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [方法] closeCon <br>
	 * [描述] 关闭连接 <br>
	 * [参数] TODO(对参数的描述) <br>
	 * [返回] void <br>
	 * [时间] 2017-4-27 下午05:35:46 <br>
	 *********************************************************.<br>
	 */
	public void closeCon(Connection conn, PreparedStatement pstmt,ResultSet rs,Statement  pstm) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
			if(pstm!=null){
				pstm.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
