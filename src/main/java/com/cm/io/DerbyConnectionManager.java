package com.cm.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbyConnectionManager {

	private static Connection connection;
	
	protected DerbyConnectionManager(){};
	
	public static Connection getConnection(){
		if (connection==null){
			try{
				DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
				String os = System.getProperty("os.name");
				if(os.contains("Mac")){
					connection= DriverManager.getConnection("jdbc:derby:\\opt\\BIRST\\ts;create=true");
				}else if(os.contains("Windows")){
					connection = DriverManager.getConnection("jdbc:derby:c:\\opt\\BIRST\\ts;create=true");
				}else{
					connection= DriverManager.getConnection("jdbc:derby:\\opt\\BIRST\\ts;create=true");
				}
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
			
			try{
				Statement stmt = connection.createStatement();
				stmt.executeUpdate("Create table CACHE (cache_key varchar(100) NOT NULL, cache_value varchar(200) NOT NULL, CONSTRAINT primary_key PRIMARY KEY (cache_key))");
			
			}catch(SQLException sqlex){
				if(sqlex.getSQLState().equals("X0Y32")){
					//DO NOTHING. Table is already created.
				}else{
					sqlex.printStackTrace();
				}
			}
		}
		return connection;
	}
}
