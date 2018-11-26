package com.starupFX.startup;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {

	public ClientSocket(String forFicheStock,long Code,String IP) {
		
			try {
				Socket s=new Socket(IP,3214);  
				PrintWriter dout=new PrintWriter(s.getOutputStream());  
				dout.println(forFicheStock+"/"+Code);  
				dout.flush();  
				dout.close();  
				s.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
	}

}
