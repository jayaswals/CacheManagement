package com.cm.main;

import java.util.Scanner;

import com.cm.intf.CacheOperations;
import com.cm.intf.impl.CacheOperationsImpl;

public class CacheMain {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			String input = scanner.nextLine();
			try{
				processInput(input);
			}catch(Exception ex){
				System.out.println(ex.getMessage());
				scanner.close();
				System.exit(0);
			}
		}
		
	}

	private static void processInput(String input) throws Exception{
		if(input!=null){
			CacheOperations operation =new CacheOperationsImpl();
			String[] inputArr = input.split("\\s+");
			if(inputArr[0].equals("CREATE")){
				if(inputArr.length!=2){
					System.out.println("Invalid format. Provide some data to create.");
				}else{
					operation.add(inputArr[1]);
				}
			}else if(inputArr[0].equals("UPDATE")){
				if(inputArr.length!=2){
					System.out.println("Invalid format. Provide some data to update.");
				}else{
					operation.update(inputArr[1]);
				}
			}else if(inputArr[0].equals("DELETE")){
				if(inputArr.length!=2){
					System.out.println("Invalid format. Provide some key to delete.");
				}else{
					operation.delete(inputArr[1]);
				}
			}else if(inputArr[0].equals("GETALL")){
				operation.get(null);
			}else if(inputArr[0].equals("GET")){
				if(inputArr.length!=2){
					System.out.println("Invalid format. Provide some data to update.");
				}else{
					operation.get(inputArr[1]);
				}
			}else if(inputArr[0].equalsIgnoreCase("EXIT")){
				throw new Exception("Bye Bye");
			}else{
				System.err.println("NO VALID OPERATION.");
				System.out.println("Valid Operations are > CREATE, UPDATE,DELETE, GETALL and GET");
				System.out.println("to Exit - type EXIT");
			}
		}
	}
	
}
