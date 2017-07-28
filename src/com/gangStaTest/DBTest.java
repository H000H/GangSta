package com.gangStaTest;

import java.sql.Connection;
import java.sql.SQLException;

import com.gangSta.util.DBConnection;

public class DBTest {

	public static void main(String[] args) throws SQLException {
		
		Connection con = DBConnection.getConnection();
		System.out.println(con);
		
		
	}
}
