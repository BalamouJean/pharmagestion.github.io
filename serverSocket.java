package com.starupFX.startup;

import java.io.*;
import java.net.*;

public class serverSocket {
	ServerSocket ss=null;
	Socket s=null;
	public serverSocket()
	{
		// TODO Auto-generated constructor stub
		  
	        try { 
	        		ServerSocket serverSocket = new ServerSocket(3214);
		            while (true) 
		            	{
			            	new MultiServerThread(serverSocket.accept()).start();
			        	}
			    } catch (IOException e) {
		            System.err.println("Could not listen on port \n"+e);
		            
		        }
	        
	}

}
