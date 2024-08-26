package edu.ifsp.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	
	private DatabaseConnector() { }
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}		
	}
	
	public static Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3307/loja?" + 
				"user=root&password=root");
		
		return conn;
	}

}