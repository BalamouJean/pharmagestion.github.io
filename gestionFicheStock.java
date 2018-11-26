package com.starupFX.startup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class gestionFicheStock {

public void createFicheStock(int code)
	{	
		try{	
			FileWriter  file=new FileWriter(new File("C:\\pharmacieDOC\\pharmaPDF\\FicheDeStocks\\"+code+".dat"));
			       
		} catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
public void remplirFicheStock(int code,String newLine){
		try {
			FileWriter modifier=new FileWriter(new File("C:\\pharmacieDOC\\pharmaPDF\\FicheDeStocks\\"+code+".dat"),true);
			BufferedWriter bw=new BufferedWriter(modifier);
			bw.write(newLine);
			bw.newLine();
			bw.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
public void createFicheDetailJour(String nomfichierJour)
{	
	 FileWriter file=null;
	 File detailJour=new File("C:\\pharmacieDOC\\pharmaPDF\\DetailVentes\\"+nomfichierJour+".dat");
	try{	
		
			File directory=new File("C:\\pharmacieDOC\\pharmaPDF\\DetailVentes");
		   if (!directory.exists())
		  		{
			   		directory.mkdir();
		  		}
		  	
			  if(!detailJour.exists())
				  file=new FileWriter(new File("C:\\pharmacieDOC\\pharmaPDF\\DetailVentes\\"+nomfichierJour+".dat"));
		  	 
	} catch (IOException e) {
        e.printStackTrace();
    }
}

public void remplirFicheSDetailJour(String nomFichierJour,String newLine){
	try {
		FileWriter modifier=new FileWriter(new File("C:\\pharmacieDOC\\pharmaPDF\\DetailVentes\\"+nomFichierJour+".dat"),true);
		BufferedWriter bw=new BufferedWriter(modifier);
		bw.write(newLine);
		bw.newLine();
		bw.close();
	
	} catch (IOException e) {
		e.printStackTrace();
	}
}
public void lireDansFichedeStock(int code,String aLine)
{
	
	
			//catfullText=aLine.split(",");
	/*
	 * 
	 * 
	 * 
	 *  
            forFicheStock=catfullText[0];
            CodeProdui=catfullText[1];
	 * 
	 * 
	 * */


}
	
}
