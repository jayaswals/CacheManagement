package com.cm.intf.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cm.intf.CacheOperations;
import com.cm.io.DerbyConnectionManager;

public class CacheOperationsImpl implements CacheOperations {
	
	private Connection conn = DerbyConnectionManager.getConnection();
	
	public void add(String input) {
			String cache[] = input.split("=");
			if(cache.length!=2){
				System.out.println("Send Value in Key Value Pair. E.g. CREATE key=value");
			}else{
				try{
					Statement stmt =  conn.createStatement();
					stmt.executeUpdate("insert into cache(cache_key, cache_value) values ('"+cache[0]+"','"+cache[1]+"')");
					stmt.close();
				}catch(SQLException sqle){
					if(sqle.getSQLState().equals("23505")){
						System.out.println("## Key '"+cache[0]+"' already exists ##");
					}
					sqle.printStackTrace();
				}
			}
	}

	public void update(String input) {
		
			String cache[] = input.split("=");
			if(cache.length!=2){
				System.out.println("Send Value as Key Value Pair. E.g. UPDATE key=value");
			}else{
				try{
					Statement stmt =  conn.createStatement();
					int count= stmt.executeUpdate("update Cache set cache_value='"+cache[1]+"' where cache_key='"+cache[0]+"'");
					if(count==0){
						System.out.println("## Key '"+cache[0]+"' does not exist ##");
					}
					stmt.close();
				}catch(SQLException sqle){
					sqle.printStackTrace();
				}
			}
	}

	public void delete(String input) {
		String cache[] = input.split("=");
		if(cache.length!=1){
			System.out.println("Send key to delete. E.g. Delete key");
		}else{
			try{
				Statement stmt =  conn.createStatement();
				int count= stmt.executeUpdate("DELETE from Cache where cache_key='"+cache[0]+"'");
				if(count==0){
					System.out.println("## Key '"+cache[0]+"' does not exist ##");
				}
				stmt.close();
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		
	}

	public void get(String input) {
		StringBuilder builder  = new StringBuilder();
		if(input==null){
			//GetALL
			try{
				Statement stmt =  conn.createStatement();
				ResultSet rs=   stmt.executeQuery("select cache_key, cache_value from cache");
				while(rs.next()){
					builder.append(rs.getString("cache_key")).append("=").append(rs.getString("cache_value")).append(System.lineSeparator());
				}
				System.out.println(builder.toString());
				rs.close();
				stmt.close();
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
			
		}else{
			//GET Key
			String cache[] = input.split("=");
			if(cache.length!=1){
				System.out.println("Send <key>. E.g. GET key");
			}else{
				try{
					Statement stmt =  conn.createStatement();
					ResultSet rs=   stmt.executeQuery("select  cache_value from cache where cache_key='"+cache[0]+"'");
					while(rs.next()){
						builder.append(rs.getString("cache_value")).append(System.lineSeparator());
					}
					System.out.println(builder.toString());
					rs.close();
					stmt.close();
				}catch(SQLException sqle){
					sqle.printStackTrace();
				}
			}
		}
	}

	
	
}
