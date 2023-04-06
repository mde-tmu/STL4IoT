package smarthub.ui;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Date;
import java.util.Scanner; // Import the Scanner class to read text files


import java.awt.event.*;

import smarthub.java.SmartHubSystem;

public class Simulation{
	
	public static char input[];
	
	  public static void ReadInputFile() {
		  Statecharts_Initializer.LogReport.add("["+Statecharts_Initializer.formatter.format(new Date())+")]: Simulation Environment Running...\n");
	   
		    try { //READ FILE IF EXISTS
		    	System.out.println("Input Directory: " + System.getProperty("user.dir"));
			    File myObj = new File("input.txt");
			    Scanner myReader = new Scanner(myObj);
			    
			    while (myReader.hasNextLine()) {
			       String data = myReader.nextLine();
			       
			       StringToChar(data);
			    }
			    
		      myReader.close();
		      
		    } catch (FileNotFoundException e) { //ERROR HANDLER
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		  }

	//Convert String to Char Array
	public static void StringToChar(String data) {

	       //System.out.println(data);
	       input = data.toCharArray(); 
	       
	       for(int i=0 ; i<input.length; i++){
	    	   System.out.print(input[i]+","); 
	       }  

	}

	public static void SendInputFileToStatechart(SmartHubSystem SmartHubSystem) {
		
	}
}