package com.birst.intf.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import com.cm.intf.CacheOperations;
import com.cm.intf.impl.CacheOperationsImpl;
import com.cm.io.DerbyConnectionManager;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CacheOperationsTest extends TestCase{

	
	private Connection conn;
	private CacheOperations operations;
	Random randomGenerator;
	protected void setUp(){
		conn= DerbyConnectionManager.getConnection();
		operations = new CacheOperationsImpl();
		 randomGenerator = new Random();
	}
	
	public void testCreatePositive(){
		int random = randomGenerator.nextInt(100000);
		String test = "test"+random;
		operations.add(test+"=testresult");
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select cache_value from Cache where cache_key='"+test+"'");
			while(rs.next()){
				String value = rs.getString("cache_value");
				if(null==value){
					Assert.fail();
				}else if(!value.equals("testresult")){
					Assert.fail();
				}
			}

		}catch(SQLException sqle){
			sqle.printStackTrace();
			Assert.fail();
		}
	}
	
	public void testDeletePositive(){
		int random = randomGenerator.nextInt(100000);
		String test = "test"+random;
		operations.add(test+"=testresult");
		operations.delete(test);
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select cache_value from Cache where cache_key='"+test+"'");
			while(rs.next()){
				String value = rs.getString("cache_value");
				if(null!=value ||value.equals("testresult")){
					Assert.fail();
				}
			}

		}catch(SQLException sqle){
			sqle.printStackTrace();
			Assert.fail();
		}
	}
	
	/*public void testCreatePositiveOutPut(){
		int random = randomGenerator.nextInt(100000);
		String test = "test"+random;
		operations.add(test+"=testresult");
		
	}*/
}
