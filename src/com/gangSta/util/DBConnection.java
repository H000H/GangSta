package com.gangSta.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	
	private static Properties props = null;
	//只在DBConnection类被加载时执行一次
	static{
		//给props进行初始化，即加载dbconfig.properties文件到props对象中
		try {
		//加载配置文件
				InputStream in = DBConnection.class.getClassLoader()
						.getResourceAsStream("db.properties");
				props = new Properties();
			
					props.load(in);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				
		//加载驱动类
				try {
					Class.forName(props.getProperty("driverClassName"));
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}
				
	}
	public static Connection getConnection() throws SQLException{
		/*
		 * 1.加载配置文件
		 * 2.加载驱动类
		 * 3.调用DriverManager.Connection()
		 */
		
		//得到Connection
		return DriverManager.getConnection(props.getProperty("url"),
				props.getProperty("username"),
				props.getProperty("password"));
			
	}
}
