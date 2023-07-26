package com.xk.util;





import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class DbConnection2 {
	public static Connection conn;
	public static PreparedStatement ps;
	public static ResultSet rs;
	
	
	public static Connection getConn() {
		try {
			//读取dataSource.properties JDBC连接
			ResourceBundle rb=ResourceBundle.getBundle("dataSource");
			String driver = rb.getString("driver");
			String dbUrl  =  rb.getString("url");
			String dbUsername = rb.getString("username");
			String dbPassword = rb.getString("password");
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
		} catch (Exception ex) {
		}
		return conn;
	}

 
	public static void closeAll( ResultSet rs,PreparedStatement ps,Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException ex) {
		}
	}
}

	