package com.starupFX.startup;

public class historiqueVenteData 
{
	private String DATE=null;
	private String NPVensu=null;
	private String VALEUR=null;
	private String USER=null;
	historiqueVenteData(String date,String nbProVendu,String valeur,String user){
		this.DATE=date;
		this.NPVensu=nbProVendu;
		this.VALEUR=valeur;
		this.USER=user;
	}
	public String getDATE(){
		return DATE;
	}
	public void setDATE(String date){
		 this.DATE=date;
	}
	public String getNPVensu(){
		return NPVensu;
	}
	public void setNPVensu(String npvendu){
		 this.NPVensu=npvendu;
	}
	public String getVALEUR(){
		return VALEUR;
	}
	public void setVALEUR(String valeur){
		 this.VALEUR=valeur;
	}
	public String getUSER(){
		return USER;
	}
	public void setUSER(String user){
		 this.USER=user;
	}
}
