package com.starupFX.startup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class verifierCoflicConnection 
{	private static String url="",ip="",InformText="serveur error: ",detail="";
	private static Connection connection = null;
	private boolean Serveur=false;

	public String verifierConfliConection()
		{
		
				
				String content="000.020.000.000";
				File fil=new File("C:\\IPadress\\password.dat");
				if(fil.exists() == true)
				{
					
					try {
						content = new String(Files.readAllBytes(Paths.get("C:\\IPadress\\password.dat")));
					} catch (IOException e) {
						//e.printStackTrace();
						System.out.println("IP adress not Found");
					}
				}else {
						//JOptionPane.showMessageDialog(null, "IP adress not found");
						System.out.println("IP adress FILE not Found");
						detail="Check IP permition";
					}
				url = "jdbc:mysql://"+content+":3306/dbproduits?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
				ip=content;
				
				 try { 
					 connection = DriverManager.getConnection(url,"balamou","jean");
					 InformText="Client side";
					 Serveur=false;
					 detail="...";
				    } catch (Exception e) {
				    	//System.out.println(e);
				    	url = "jdbc:mysql://localhost/dbproduits?useSSL=false";
				    	//**********verifier conection au serveur
				    	try { 
							 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbproduits?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", "balamou", "jean");
							 ip=null;
							// @SuppressWarnings("unused")
							//serverSocket serverSocket=new serverSocket();
							 InformText="Server side";
							 Serveur=true;
							 detail="...";
						    } catch (Exception ex) {
						    	System.out.println(ex);
						    	detail=ex.getMessage();
						    	//detail=e.getMessage();//detail="Check IP and GRANT permition";
						    }finally {
						      if (connection != null) {
						        try {
						        	connection.close();
						        } catch (SQLException ex) {
						        } // nothing we can do
						      }
						    }
				    	
				    }finally {
				      if (connection != null) {
				        try {
				        	connection.close();
				        } catch (SQLException e) {
				        } // nothing we can do
				      }
				    }
				//***************verifier la connection local*********************
				 
				 
		return url;
	}
	
	public String Information(){
		return InformText+detail;
	}
	
	public String IPserver(){
		return ip;// for socket and fiche stock
	}
	public boolean serveur(){
		return Serveur;// for socket and fiche stock
	}
	
	
}
